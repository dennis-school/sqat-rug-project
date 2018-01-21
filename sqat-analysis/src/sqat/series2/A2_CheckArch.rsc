module sqat::series2::A2_CheckArch

import sqat::series2::Dicto;
import lang::java::jdt::m3::Core;
import Message;
import ParseTree;
import IO;
import Set;
import String;

/*

This assignment has two parts:
- write a dicto file (see example.dicto for an example)
  containing 3 or more architectural rules for Pacman
  
- write an evaluator for the Dicto language that checks for
  violations of these rules. 

Part 1  

An example is: ensure that the game logic component does not 
depend on the GUI subsystem. Another example could relate to
the proper use of factories.   

Make sure that at least one of them is violated (perhaps by
first introducing the violation).

Explain why your rule encodes "good" design.
  
  nl.tudelft.jpacman.sprite cannot import nl.tudelft.jpacman.board
  : The "sprite" package appears to be a completely independent package,
    that is behaving independently from the other packages. Therefore
    it should not depend on the game model (board).
    VIOLATED !
  nl.tudelft.jpacman.ui must import nl.tudelft.jpacman.board
  : The view (GUI) must depend on the model (board), if this is not true
    the MVC pattern would not be satisfied
  nl.tudelft.jpacman.ui.BoardPanel must depend nl.tudelft.jpacman.board
  : The specific BoardPanel view must depend on the model of the board
  
Part 2:  
 
Complete the body of this function to check a Dicto rule
against the information on the M3 model (which will come
from the pacman project). 

A simple way to get started is to pattern match on variants
of the rules, like so:

switch (rule) {
  case (Rule)`<Entity e1> cannot depend <Entity e2>`: ...
  case (Rule)`<Entity e1> must invoke <Entity e2>`: ...
  ....
}

Implement each specific check for each case in a separate function.
If there's a violation, produce an error in the `msgs` set.  
Later on you can factor out commonality between rules if needed.

The messages you produce will be automatically marked in the Java
file editors of Eclipse (see Plugin.rsc for how it works).

Tip:
- for info on M3 see series2/A1a_StatCov.rsc.

Questions
- how would you test your evaluator of Dicto rules? (sketch a design)
  : Provide (at least) 1 test case for each rule type
- come up with 3 rule types that are not currently supported by this version
  of Dicto (and explain why you'd need them). 
  : - Calls/dependencies by anonymous inner classes
    - Super method calls
    - Call to overloaded methods
*/


set[Message] eval(start[Dicto] dicto, M3 m3) = eval(dicto.top, m3);

set[Message] eval((Dicto)`<Rule* rules>`, M3 m3) 
  = ( {} | it + eval(r, m3) | r <- rules );

// The locations of the classes in the package
set[loc] classes( Entity package, M3 m3 )
  = { c | c <- classes(m3) + enums(m3) + interfaces(m3), startsWith( c.path, "/" + replaceAll( "<package>", ".", "/" ) ) };

// The locations of the classes imported by the class
set[loc] classImports( loc class, M3 m3 )
  = { d | d <- m3.typeDependency[ class ] } + { d | d <- m3.typeDependency[ declaredMethods( m3 )[ class ] ] };

// Relation between all classes in the given package, and the classes imported by these classes
rel[loc,loc] packageImports( Entity package, M3 m3 )
  = { <c,d> | c <- packageClasses, d <- classImports( c, m3 ), isClass( d ) || isEnum( d ) || isInterface( d ) }
  when packageClasses := classes( package, m3 );

// Relation between all classes in the given package, and the classes imported by these classes within the 2nd package
rel[loc,loc] packageImportsBetween( Entity package1, Entity package2, M3 m3 )
  = { <c,d> | <c,d> <- packageImports( package1, m3 ), d in classes( package2, m3 ) };
  
bool isClassIn( loc classLoc, Entity package )
  = startsWith( classLoc.path, "/" + replaceAll( "<package>", ".", "/" ) );

// package imports package
set[Message] eval( (Rule)`<Entity e1> cannot import <Entity e2>`, M3 m3 ) {
  set[Message] msgs = {};
  
  for ( <importer,imported> <- packageImportsBetween( e1, e2, m3 ) ) {
    str importedClass = substring(replaceAll( imported.path, "/", "." ), 1);
    msgs = msgs + warning( "Architectural violation. Depends on class <importedClass>", importer );
  }
  
  return msgs;
}

// package imports package
set[Message] eval( (Rule)`<Entity e1> must import <Entity e2>`, M3 m3 ) {
  if ( size( [ imported | <importer,imported> <- packageImports( e1, m3 ), isClassIn( imported, e2 ) ] ) == 0 ) {
    return { warning( "Architectural violation. <e1> must import <e2>", e1@\loc ) };
  } else {
    return { };
  }
}

// class depends on package
set[Message] eval( (Rule)`<Entity e1> must depend <Entity e2>`, M3 m3 ) {
  loc classLoc = toList( classes( e1, m3 ) )[ 0 ];
  
  if ( size( [ imported | imported <- classImports( classLoc, m3 ), isClassIn( imported, e2 ) ] ) == 0 ) {
    return { warning( "Architectural violation. <e1> must depend on <e2>", e1@\loc ) };
  } else {
    return { };
  }
}

M3 jpacmanM3( ) = createM3FromEclipseProject(|project://jpacman|);
start[Dicto] constraints( ) = parse(#start[Dicto],|project://sqat-analysis/src/sqat/series2/constraints.dicto|);

set[Message] main( )
  = eval( constraints( ), jpacmanM3( ) );
  