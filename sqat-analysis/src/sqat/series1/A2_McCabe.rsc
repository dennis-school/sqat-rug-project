module sqat::series1::A2_McCabe

import lang::java::jdt::m3::AST;
import Relation;
import Set;
import Map;
import IO;
import util::FileSystem;
import sqat::series1::A1_SLOC;
import sqat::series1::SLOCGrammar;
import ParseTree;
import analysis::statistics::Correlation;

/*

Construct a distribution of method cylcomatic complexity. 
(that is: a map[int, int] where the key is the McCabe complexity, and the value the frequency it occurs)


Questions: All answers in the main method
- which method has the highest complexity (use the @src annotation to get a method's location)

- how does pacman fare w.r.t. the SIG maintainability McCabe thresholds?

- is code size correlated with McCabe in this case (use functions in analysis::statistics::Correlation to find out)? 
  (Background: Davy Landman, Alexander Serebrenik, Eric Bouwers and Jurgen J. Vinju. Empirical analysis 
  of the relationship between CC and SLOC in a large corpus of Java methods 
  and C functions Journal of Software: Evolution and Process. 2016. 
  http://homepages.cwi.nl/~jurgenv/papers/JSEP-2015.pdf)
  
- what if you separate out the test sources?

Tips: 
- the AST data type can be found in module lang::java::m3::AST
- use visit to quickly find methods in Declaration ASTs
- compute McCabe by matching on AST nodes

Sanity checks
- write tests to check your implementation of McCabe
  : Verify the most complex method as a sanity check
  
Bonus
- write visualization using vis::Figure and vis::Render to render a histogram.

*/

set[Declaration] jpacmanASTs() = createAstsFromEclipseProject(|project://jpacman|, true);
set[Declaration] jpacmanMainASTs() = { k | k <- jpacmanASTs( ), uriBase(k.src) in files(|project://jpacman/src/main/|) };
set[Declaration] jpacmanTestASTs() = { k | k <- jpacmanASTs( ), uriBase(k.src) in files(|project://jpacman/src/test/|) };

alias CC = rel[loc method, int cc];

loc uriBase(loc l) = |<l.scheme>://<l.authority><l.path>|;

CC cc(set[Declaration] decls) {
  CC result = { };
  
  for ( Declaration decl <- decls ) {
    visit ( decl ) {
    case Declaration d:
      switch ( d ) {
      	case \method(Type \return, str name, list[Declaration] parameters, list[Expression] exceptions, Statement impl):
	      result[d.src] = cc(impl);
	    case \constructor(str name, list[Declaration] parameters, list[Expression] exceptions, Statement impl):
	      result[d.src] = cc(impl);
      }
    }
  }
  
  return result;
}

int cc(Statement impl) {
  int result = 1;
  visit (impl) {
    case \if(_,_) : result += 1;
    // Note that an if-else statement only introduces 1 extra branch
    case \if(_,_,_) : result += 1;
    case \case(_) : result += 1;
    case \do(_,_) : result += 1;
    case \while(_,_) : result += 1;
    case \for(_,_,_) : result += 1;
    case \for(_,_,_,_) : result += 1;
    case \foreach(_,_,_) : result += 1;
    case \catch(_,_): result += 1;
    case \conditional(_,_,_): result += 1;
    case infix(_,"&&",_) : result += 1;
    case infix(_,"||",_) : result += 1;
  }
  return result;
}

alias CCDist = map[int cc, int freq];

CCDist ccDist(CC cc) {
  CCDist dist = ( c: 0 | <loc _,int c> <- cc );
  for ( <loc method,int complexity> <- cc ) {
    dist[ complexity ] += 1;
  }
  return dist;
}

loc findComplexestFile( map[loc,int] ccMap )
  = (toList(domain(ccMap))[0]|ccMap[l]>ccMap[it]?l:it|loc l <- ccMap);

alias MethodSLOC = map[loc method, int sloc];

MethodSLOC methodSloc(list[loc] locs)
  = ( l: sloc( readFile( l ) ) | l <- locs );

num ccToSlocCorrelation(set[Declaration] decls) {
  map[loc,int] ccMap = ( method: complexity | <loc method,int complexity> <- cc(decls) );
  MethodSLOC methodSlocs = methodSloc( [ l | l <- ccMap ] );
  lrel[num,num] ccToSloc = [ <ccMap[l],methodSlocs[l]> | l <- ccMap ];
  return PearsonsCorrelation( ccToSloc );
}

void main( ) {
  // This is applying a "cheat" to convert a relation to a max-map
  map[loc,set[int]] ccSetMap = index( cc( jpacmanASTs( ) ) );
  ccMap = ( k: max(ccSetMap[k]) | k <- ccSetMap );
  loc complexFile = findComplexestFile(ccMap);
  print( "Method with highest complexity: " );
  println( complexFile );
  print( "Highest method complexity: " );
  println( ccMap[complexFile] );
  println( );
  
  println( "Following the risks mentioned in the SIG paper," );
  println( "CComplexity values in the range 1-10 are simple, without much risk" );
  println( "So all methods in JPacman are simple w.r.t. the SIG Maintainability threshold" );
  println( "And would be rated ++ w.r.t. to risk" );
  println( );
  
  print( "Method size correlation to method CC: " );
  println( ccToSlocCorrelation( jpacmanASTs( ) ) );
  
  print( "Non-test method size correlation to method CC: " );
  println( ccToSlocCorrelation( jpacmanMainASTs( ) ) );
  
  // This one is clearly not so good
  // because test cases have a very low CC (typically 1)
  // Though their SLOC varies, resulting in a bad correlation
  print( "Test method size correlation to method CC: " );
  println( ccToSlocCorrelation( jpacmanTestASTs( ) ) );
}