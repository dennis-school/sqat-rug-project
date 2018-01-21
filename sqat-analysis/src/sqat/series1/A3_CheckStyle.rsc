module sqat::series1::A3_CheckStyle

import Java17ish;
import Message;
import util::FileSystem;
import ParseTree;
import IO;
import Set;
import util::ResourceMarkers;

/*

Assignment: detect style violations in Java source code.
Select 3 checks out of this list:  http://checkstyle.sourceforge.net/checks.html
Compute a set[Message] (see module Message) containing 
check-style-warnings + location of  the offending source fragment. 

Plus: invent your own style violation or code smell and write a checker.

Note: since concrete matching in Rascal is "modulo Layout", you cannot
do checks of layout or comments (or, at least, this will be very hard).

JPacman has a list of enabled checks in checkstyle.xml.
If you're checking for those, introduce them first to see your implementation
finds them.

Questions
- for each violation: look at the code and describe what is going on? 
  Is it a "valid" violation, or a false positive?

Tips 

- use the grammar in lang::java::\syntax::Java15 to parse source files
  (using parse(#start[CompilationUnit], aLoc), in ParseTree)
  now you can use concrete syntax matching (as in Series 0)

- alternatively: some checks can be based on the M3 ASTs.

- use the functionality defined in util::ResourceMarkers to decorate Java 
  source editors with line decorations to indicate the smell/style violation
  (e.g., addMessageMarkers(set[Message]))

  
Bonus:
- write simple "refactorings" to fix one or more classes of violations 

*/

// avoid star import
// default comes last
// general exception catching

set[Message] checkStarImport( start[CompilationUnit] cu ) {
  set[Message] result = {};
  visit( cu ) {
  case ImportDec i: {
    switch ( i ) {
    case (ImportDec)`import <PackageName _>.*;`: {
      result += warning( "Star import", i@\loc );
    }
    }
  }
  }
  return result;
}

set[Message] checkLastSwitchDefault( start[CompilationUnit] cu ) {
  set[Message] result = {};
  
  visit ( cu ) {
  case SwitchBlock g: {
    bool hadDefault = false;
    bool isOkay = true;
    loc defaultLocation;
    
    visit ( g ) {
    case SwitchLabel l: {
      switch ( l ) {
	    case (SwitchLabel)`default:`: {
	      hadDefault = true;
	      defaultLocation = l@\loc;
	    }
	    case (SwitchLabel)`case <Expr _>:`: {
	      if ( hadDefault )
	        isOkay = false;
	    }
      }
    }
    }
    
    if ( !isOkay ) {
      result += warning( "Default case not at the end", defaultLocation );
    }
  }
  }
  return result;
}

set[Message] checkGeneralExceptionCatching( start[CompilationUnit] cu ) {
  set[Message] result = {};
  
  visit ( cu ) {
  case CatchClause clause: {
    visit( clause ) {
    case FormalParam p: {
      switch ( p ) {
	  case (FormalParam)`Exception <VarDecId d>`:
	    result += warning( "Catching a general exception", p@\loc );
	  case (FormalParam)`RuntimeException <VarDecId d>`:
	    result += warning( "Catching a general exception", p@\loc );
	  case (FormalParam)`Error <VarDecId d>`:
	    result += warning( "Catching a general exception", p@\loc );
      }
    }
    }
  }
  }
  
  return result;
}

set[Message] checkAssignments( Expr expression ) {
  set[Message] result = {};
  
  visit ( expression ) {
  case Expr subExpr: {
    switch ( subExpr ) {
    case (Expr)`<LHS _> *= <Expr _>`: { result += warning( "Assignment used as value", subExpr@\loc ); }
    case (Expr)`<LHS _> |= <Expr _>`: { result += warning( "Assignment used as value", subExpr@\loc ); }
    case (Expr)`<LHS _> &= <Expr _>`: { result += warning( "Assignment used as value", subExpr@\loc ); }
    case (Expr)`<LHS _> \>\>= <Expr _>`: { result += warning( "Assignment used as value", subExpr@\loc ); }
    case (Expr)`<LHS _> %= <Expr _>`: { result += warning( "Assignment used as value", subExpr@\loc ); }
    case (Expr)`<LHS _> += <Expr _>`: { result += warning( "Assignment used as value", subExpr@\loc ); }
    case (Expr)`<LHS _> ^= <Expr _>`: { result += warning( "Assignment used as value", subExpr@\loc ); }
    case (Expr)`<LHS _> = <Expr _>`: { result += warning( "Assignment used as value", subExpr@\loc ); }
    case (Expr)`<LHS _> /= <Expr _>`: { result += warning( "Assignment used as value", subExpr@\loc ); }
    case (Expr)`<LHS _> \>\>\>= <Expr _>`: { result += warning( "Assignment used as value", subExpr@\loc ); }
    case (Expr)`<LHS _> -= <Expr _>`: { result += warning( "Assignment used as value", subExpr@\loc ); }
  }
  }
  }
  
  return result;
}

set[Message] checkAssignmentAsValue( start[CompilationUnit] cu ) {
  set[Message] result = {};
  
  visit ( cu ) {
  case VarDec v: {
    visit ( v ) {
    case Expr e: { result = union( { result, checkAssignments( e ) } ); }
    }
  }
  case ConstrInv v: {
    visit ( v ) {
    case Expr e: { result = union( { result, checkAssignments( e ) } ); }
    }
  }
  case (Expr)`<LHS _> *= <Expr e>`:  { result = union( { result, checkAssignments( e ) } ); }
  case (Expr)`<LHS _> |= <Expr e>`:  { result = union( { result, checkAssignments( e ) } ); }
  case (Expr)`<LHS _> &= <Expr e>`:  { result = union( { result, checkAssignments( e ) } ); }
  case (Expr)`<LHS _> \>\>= <Expr e>`:  { result = union( { result, checkAssignments( e ) } ); }
  case (Expr)`<LHS _> %= <Expr e>`:  { result = union( { result, checkAssignments( e ) } ); }
  case (Expr)`<LHS _> += <Expr e>`:  { result = union( { result, checkAssignments( e ) } ); }
  case (Expr)`<LHS _> ^= <Expr e>`:  { result = union( { result, checkAssignments( e ) } ); }
  case (Expr)`<LHS _> = <Expr e>`:  { result = union( { result, checkAssignments( e ) } ); }
  case (Expr)`<LHS _> /= <Expr e>`:  { result = union( { result, checkAssignments( e ) } ); }
  case (Expr)`<LHS _> \>\>\>= <Expr e>`:  { result = union( { result, checkAssignments( e ) } ); }
  case (Expr)`<LHS _> -= <Expr e>`:  { result = union( { result, checkAssignments( e ) } ); }
  }
  
  return result;
}

set[Message] checkStyle(loc project) {
  set[Message] result = {};
  
  for ( loc l <- find( project, "java" ) ) {
    start[CompilationUnit] cu = parse(#start[CompilationUnit], l, allowAmbiguity=true);
    
    result = union( { result, checkStarImport( cu ) } );
    result = union( { result, checkLastSwitchDefault( cu ) } );
    result = union( { result, checkGeneralExceptionCatching( cu ) } );
    result = union( { result, checkAssignmentAsValue( cu ) } );
  }
  
  return result;
}

void main( ) {
  set[Message] msgs = checkStyle(|project://jpacman/|);
  println( "Found style violations" );
  for ( Message msg <- msgs ) {
    println( msg );
  }
  addMessageMarkers(msgs);
}