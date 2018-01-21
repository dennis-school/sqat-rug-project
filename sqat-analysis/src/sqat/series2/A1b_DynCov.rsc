module sqat::series2::A1b_DynCov

import Java17ish;
import ParseTree;
import util::FileSystem;
import util::Math;
import IO;
import lang::csv::IO;
import Relation;
import Set;

/*

Assignment: instrument (non-test) code to collect dynamic coverage data.

- Write a little Java class that contains an API for collecting coverage information
  and writing it to a file. NB: if you write out CSV, it will be easy to read into Rascal
  for further processing and analysis (see here: lang::csv::IO)

- Write two transformations:
  1. to obtain method coverage statistics
     (at the beginning of each method M in class C, insert statement `hit("C", "M")`
  2. to obtain line-coverage statistics
     (insert hit("C", "M", "<line>"); after every statement.)

The idea is that running the test-suite on the transformed program will produce dynamic
coverage information through the insert calls to your little API.

Questions
- use a third-party coverage tool (e.g. Clover) to compare your results to (explain differences)
  : Quite similar. Clover does not seem to cover the Launcher, for some reason. However, this Rascal
    implementation declines to augment several methods, which means some methods are missed.
    But overall quite similar.
- which methods have full line coverage?
  : See main
- which methods are not covered at all, and why does it matter (if so)?
  : See main. Methods that are not covered are less reliable to be correct. Though, this does not really
    matter for trivial methods.
- what are the drawbacks of source-based instrumentation?
  : The code has to be setup and run (Maven configuration in this case, dependencies installed, etc.).
    The augmentation/instrumentation can be slightly buggy, resulting in not 100% accurate results.
    There is annoying behaviour regarding dead code; if the instrumentation adds code that is unreachable
    in all cases, it will be ignored during runtime, resulting in inaccurate results.
    Also JUnit seems to abruptly abort the process, resulting in partially missing results.

Tips:
- create a shadow JPacman project (e.g. jpacman-instrumented) to write out the transformed source files.
  Then run the tests there. You can update source locations l = |project://jpacman/....| to point to the 
  same location in a different project by updating its authority: l.authority = "jpacman-instrumented"; 

- to insert statements in a list, you have to match the list itself in its context, e.g. in visit:
     case (Block)`{<BlockStm* stms>}` => (Block)`{<BlockStm insertedStm> <BlockStm* stms>}` 
  
- or (easier) use the helper function provide below to insert stuff after every
  statement in a statement list.

- to parse ordinary values (int/str etc.) into Java15 syntax trees, use the notation
   [NT]"...", where NT represents the desired non-terminal (e.g. Expr, IntLiteral etc.).  

*/

str locToStr( loc l )
  = l.scheme+"://"+l.authority+l.path+"?"+l.query+"#"+l.fragment;

// The names are not always properly extracted, because for some reason not all patterns match
// Ignore this for now, as at least the augmenting and checking are consistent.

default str name( ClassDec d ) = "unknown";
default str name( MethodDecHead h ) = "unknown";
default str name( MethodDec d ) = "unknown";

default str name( ClassDecHead h ) {
  top-down visit( h ) {
  case Id name: { return "<name>"; }
  }
  return "unknown";
}

str name( (ClassDecHead)`<Anno* _> <ClassMod _> class <Id id> <TypeParams? _> <Super? _> <Interfaces? _>` ) =
  "<id>";

str name( (ClassDec)`<ClassDecHead h> <ClassBody _>` )
  = name( h );

str name( (MethodDecHead)`<MethodMod _> <TypeParams? _> <ResultType _> <Id name> ( <{FormalParam ","}* _> ) <Throws? _>` )
  = "<name>";

str name( (MethodDec)`<MethodDecHead h> <MethodBody _>` )
  = name( h );

// ### Perform augments ###

MethodBody augment( str className, str methodName, MethodBody body ) {
  int hitNum = 0;
  StringLiteral classNameStr = parse(#StringLiteral,"\""+className+"\"");
  StringLiteral methodNameStr = parse(#StringLiteral,"\""+methodName+"\"");
  
  BlockStm blockStmLoc( loc l ) {
    hitNum += 1;
    return parse(#BlockStm, "hit(\""+className+"\",\""+methodName+"\","+toString(hitNum)+");");
  }
  
  BlockStm methodHit = parse(#BlockStm, "hit(\""+className+"\",\""+methodName+"\");");

  body = top-down-break visit( body ) {
  case (Block)`{<BlockStm+ stms>}` => (Block)`{<BlockStm methodHit> <BlockStm+ stms>}`
  }

  return visit( body ) {
  case BlockStm* stms => putAfterEvery( stms, blockStmLoc )
  }
}

MethodDec augment( str className, MethodDec d ) {
  str methodname = name( d );
  
  if ( methodname == "unknown" )
    return d;
  
  return visit( d ) {
  case MethodBody body => augment( className, methodname, body )
  }
}
  
ClassDec augment( str packagename, ClassDec d ) {
  str classname = packagename + name( d );
  
  return visit ( d ) {
  case MethodDec md => augment( classname, md )
  }
  
  return d;
}

start[CompilationUnit] augment(start[CompilationUnit] cu) {
  ImportDec coverageImport = (ImportDec)`import static nl.dennis.api.CoverageChecker.hit;`;
  str pkgname = "";
  
  return visit (cu) {
    case (PackageDec)`<Anno* _> package <PackageName name>;`: {
      pkgname = "<name>.";
    }
    case (CompilationUnit)`<PackageDec? package> <ImportDec* imports> <TypeDec* types>`
      => (CompilationUnit)`<PackageDec? package> <ImportDec* imports> <ImportDec coverageImport> <TypeDec* types>`
    case ClassDec d => augment(pkgname,d)
  }
}

void augment(loc project) {
  for ( loc l <- find( project, "java" ) ) {
    start[CompilationUnit] cu = parse(#start[CompilationUnit], l, allowAmbiguity=true);
    cu = augment( cu );
    
    l.authority = "jpacman-instrumented";
    writeFile( l, cu );
  }
}

// ### Extract and evaluate augments ###

alias MethodAugments = rel[str classname,str methodname];
alias LineAugments = map[tuple[str classname, str methodname] key,int numlines];
alias MethodHits = MethodAugments;
alias LineHits = rel[str classname,str methodname,int linehit];

int findAugments( str className, str methodName, MethodBody body ) {
  int hitNum = 0;
  StringLiteral classNameStr = parse(#StringLiteral,"\""+className+"\"");
  StringLiteral methodNameStr = parse(#StringLiteral,"\""+methodName+"\"");
  
  BlockStm blockStmLoc( loc l ) {
    hitNum += 1;
    return parse(#BlockStm, "hit(\""+className+"\",\""+methodName+"\","+toString(hitNum)+");");
  }
  
  // Keep the rest the same as augment(str,str,MethodBody), to make sure nothing is missed
  
  BlockStm methodHit = parse(#BlockStm, "hit(\""+className+"\",\""+methodName+"\");");

  body = top-down-break visit( body ) {
  case (Block)`{<BlockStm+ stms>}` => (Block)`{<BlockStm methodHit> <BlockStm+ stms>}`
  }

  visit( body ) {
  case BlockStm* stms => putAfterEvery( stms, blockStmLoc )
  }
  
  return hitNum;
}

tuple[MethodAugments,LineAugments] findAugments( str classname, MethodDec d ) {
  MethodAugments mAugs = { };
  LineAugments lAugs = ( );
  
  str methodname = name( d );
  
  if ( methodname == "unknown" )
    return <mAugs,lAugs>;
    
  visit( d ) {
  case MethodBody body: {
    int numLines = findAugments( classname, methodname, body );
    mAugs += <classname,methodname>;
    lAugs[<classname,methodname>] = numLines;
  }
  }
  
  return <mAugs,lAugs>;
}

tuple[MethodAugments,LineAugments] findAugments( str packagename, ClassDec d ) {
  MethodAugments mAugs = { };
  LineAugments lAugs = ( );
  
  str classname = packagename + name( d );
  
  visit ( d ) {
  case MethodDec md: {
    <newMAugs, newLAugs> = findAugments( classname, md );
    mAugs += newMAugs;
    lAugs += newLAugs;
  }
  }
  
  return <mAugs,lAugs>;
}

tuple[MethodAugments,LineAugments] findAugments(start[CompilationUnit] cu) {
  MethodAugments mAugs = { };
  LineAugments lAugs = ( );
  str pkgname = "";
  
  visit (cu) {
    case (PackageDec)`<Anno* _> package <PackageName name>;`: {
      pkgname = "<name>.";
    }
    case ClassDec d: {
      <newMAugs, newLAugs> = findAugments( pkgname, d );
      mAugs += newMAugs;
      lAugs += newLAugs;
    }
  }
  
  return <mAugs,lAugs>;
}

tuple[MethodAugments,LineAugments] findAugments( loc project ) {
  MethodAugments mAugs = { };
  LineAugments lAugs = ( );
  
  for ( loc l <- find( project, "java" ) ) {
    start[CompilationUnit] cu = parse(#start[CompilationUnit], l, allowAmbiguity=true);
    <newMAugs, newLAugs> = findAugments( cu );
    mAugs += newMAugs;
    lAugs += newLAugs;
  }
  
  return <mAugs,lAugs>;
}

// ### Control methods ###

void augmentMain( ) = augment( |project://jpacman/src/main/java/| );

tuple[MethodAugments,LineAugments] findAugmentsMain( ) = findAugments( |project://jpacman/src/main/java/| );

MethodHits readCsvMethodHits( )
  = readCSV(#MethodHits,  |project://jpacman-instrumented/methods-hits.csv|, separator = ",");
  
LineHits readCsvLineHits( )
  = readCSV(#LineHits,  |project://jpacman-instrumented/line-hits.csv|, separator = ",");

rel[str classname,str methodname] uncoveredMethods( ) {
  <methodAugments,_> = findAugmentsMain( );
  MethodHits methodHits = readCsvMethodHits( );
  
  return methodAugments - methodHits;
}

real methodCoverage( ) {
  <methodAugments,_> = findAugmentsMain( );
  MethodHits methodHits = readCsvMethodHits( );
  
  return ( 1.0 * size( [ a | a <- methodHits ] ) ) / size( [ a | a <- methodAugments ] );
}

map[tuple[str classname, str methodname] key, real coverage] lineCoverage( ) {
  <_,lineAugments> = findAugmentsMain( );
  LineHits lineHits = readCsvLineHits( );
  
  map[tuple[str classname, str methodname] key,set[int] hits] joinedLineHits
    = ( );
  
  for ( <classname,methodname,hit> <- lineHits ) {
    if ( <classname,methodname> in joinedLineHits ) {
      joinedLineHits[ <classname,methodname> ] += hit;
    } else {
      joinedLineHits[ <classname,methodname> ] = { hit };
    }
  }
  
  map[tuple[str classname, str methodname] key, real coverage] coverage
    = ( key: ( ( lineAugments[key] > 0 && ( key in joinedLineHits ) ) ? ( 1.0 * size( joinedLineHits[key] ) / lineAugments[key] ) : 0.0 ) | key <- lineAugments );
  
  return coverage;
}

void main( ) {
  println( "There is no main!" );
  println( "run \'augmentMain( )\' to setup the augmented shadow project" );
  println( );
  println( "Then \'methodCoverage()\' can be called to find the % of reachable methods" );
  println( "\'lineCoverage()\' can be called to find the % of lines within methods" );
  println( );
  println( "- which methods have full line coverage?" );
  lCoverage = lineCoverage( );
  println( [ key | key <- lCoverage, lCoverage[ key ] == 1.0 ] );
  println( );
  println( "- which methods are not covered at all, and why does it matter (if so)?" );
  println( uncoveredMethods( ) );
}

// Helper function to deal with concrete statement lists
// second arg should be a closure taking a location (of the element)
// and producing the BlockStm to-be-inserted 
BlockStm* putAfterEvery(BlockStm* stms, BlockStm(loc) f) {
  Block put(b:(Block)`{}`) = (Block)`{<BlockStm s>}`
    when BlockStm s := f(b@\loc);
  
  default bool isEnd(BlockStm s) = false;
  bool isEnd((BlockStm)`return <Expr? _>;`) = true;
  bool isEnd((BlockStm)`break <Id? _>;`) = true;
  bool isEnd((BlockStm)`throw <Expr _>;`) = true;
  bool isEnd((BlockStm)`try <Block b1> <CatchClause* c> finally <Block b2>`) = true;
  bool isEnd((BlockStm)`try <Block b1> <CatchClause+ c>`) = true;
  bool isEnd((BlockStm)`try ( <Type _> <VarDec _> ) <Block _> <CatchClause* _> finally <Block _>`) = true;
  bool isEnd((BlockStm)`try ( <Type _> <VarDec _> ) <Block _> <CatchClause+ _>`) = true;
  bool isEnd((BlockStm)`try ( <Type _> <VarDec _> ) <Block _>`) = true;
  bool isEnd((BlockStm)`switch ( <Expr _> ) <SwitchBlock _>`) = true;
  
  default Block put((Block)`{<BlockStm s0>}`)
    = !isEnd(s0) ? (Block)`{<BlockStm s0> <BlockStm s>}` : (Block)`{<BlockStm s0>}`
    when BlockStm s := f(s0@\loc);
  
  default Block put((Block)`{<BlockStm s0> <BlockStm+ stms>}`) 
    = !isEnd(s0) ? (Block)`{<BlockStm s0> <BlockStm s> <BlockStm* stms2>}` : (Block)`{<BlockStm s0> <BlockStm+ stms>}`
    when
      BlockStm s := f(s0@\loc),
      (Block)`{<BlockStm* stms2>}` := put((Block)`{<BlockStm+ stms>}`);

  if ((Block)`{<BlockStm* stms2>}` := put((Block)`{<BlockStm* stms>}`)) {
    return stms2;
  }
}


