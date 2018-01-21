module sqat::series2::A1a_StatCov

import IO;
import Set;
import Relation;
import util::Math;
import lang::java::jdt::m3::Core;

/*

Implement static code coverage metrics by Alves & Visser 
(https://www.sig.eu/en/about-sig/publications/static-estimation-test-coverage)


The relevant base data types provided by M3 can be found here:

- module analysis::m3::Core:

rel[loc name, loc src]        M3.declarations;            // maps declarations to where they are declared. contains any kind of data or type or code declaration (classes, fields, methods, variables, etc. etc.)
rel[loc name, TypeSymbol typ] M3.types;                   // assigns types to declared source code artifacts
rel[loc src, loc name]        M3.uses;                    // maps source locations of usages to the respective declarations
rel[loc from, loc to]         M3.containment;             // what is logically contained in what else (not necessarily physically, but usually also)
list[Message]                 M3.messages;                // error messages and warnings produced while constructing a single m3 model
rel[str simpleName, loc qualifiedName]  M3.names;         // convenience mapping from logical names to end-user readable (GUI) names, and vice versa
rel[loc definition, loc comments]       M3.documentation; // comments and javadoc attached to declared things
rel[loc definition, Modifier modifier] M3.modifiers;     // modifiers associated with declared things

- module  lang::java::m3::Core:

rel[loc from, loc to] M3.extends;            // classes extending classes and interfaces extending interfaces
rel[loc from, loc to] M3.implements;         // classes implementing interfaces
rel[loc from, loc to] M3.methodInvocation;   // methods calling each other (including constructors)
rel[loc from, loc to] M3.fieldAccess;        // code using data (like fields)
rel[loc from, loc to] M3.typeDependency;     // using a type literal in some code (types of variables, annotations)
rel[loc from, loc to] M3.methodOverrides;    // which method override which other methods
rel[loc declaration, loc annotation] M3.annotations;

Tips
- encode (labeled) graphs as ternary relations: rel[Node,Label,Node]
- define a data type for node types and edge types (labels) 
- use the solve statement to implement your own (custom) transitive closure for reachability.

Questions:
- what methods are not covered at all?
  : See output. Mainly methods that are called by run()
- how do your results compare to the jpacman results in the paper? Has jpacman improved?
  : The paper mentions 88.06% static coverage, whereas this program reports 74.86% static coverage
    Either JPacMan got worse, or this tool evaluates the coverage less lenient
- use a third-party coverage tool (e.g. Clover) to compare your results to (explain differences)
  : Paper says 93.53% by Clover. My Clover run displays 76.4% coverage. Quite similar.

*/

M3 jpacmanM3() = createM3FromEclipseProject(|project://jpacman|);

void main( ) {
  M3 model = jpacmanM3( );
  
  set[loc] allMethods = { t[1] | t <- declaredMethods(model), !isConstructor( t[1] ) };
  // These are all the relations as described in the paper
  rel[loc,str,loc] methodCalls = { <caller,"call",callee> | caller <- allMethods, callee <- model.methodInvocation[ caller ], !isConstructor( callee ) };
  rel[loc,str,loc] methodInit = { <caller,"init",callee> | caller <- allMethods, constr <- model.methodInvocation[ caller ], isConstructor( constr ), callee <- model.methodInvocation[ constr ], !isConstructor( callee ) };
  rel[loc,str,loc] methodDefs = { <d[0],"def",d[1]> | d <- declaredMethods(model) };
  rel[loc,str,loc] methodInvokes = { <c[0],"invoke",c[2]> | c <- methodCalls } + { <c[0],"invoke",c[2]> | c <- methodInit };
  rel[loc,str,loc] methodVirtualInvokes = { <c[0],"virtual",ov> | c <- methodInvokes, ov <- invert(model.methodOverrides)[c[2]] };
  
  set[loc] testMethods = { a | <a,b> <- model.annotations, b in { |java+interface:///org/junit/Test|, |java+interface:///org/junit/Before|, |java+interface:///org/junit/After| } };
  rel[loc,str,loc] methodInvokesTrans = { <c[0],"invoke",c[1]> | c <- ({ <e[0],e[2]> | e <- methodInvokes + methodVirtualInvokes }+) };
  set[loc] reachableMethods = { entry[1] | caller <- testMethods, entry <- methodInvokesTrans[caller] };
  set[loc] unreachableMethods = allMethods - reachableMethods - testMethods;
  
  println( "Unreachable methods:" );
  for ( m <- unreachableMethods )
    println( m );
  
  rel[loc,loc] classMethods = { d | d <- declaredMethods(model), !isConstructor( d[1] ) };
  rel[loc,real] classCoverage = { <c, ( 1.0 * size( ( reachableMethods & classMethods[c] ) - testMethods) ) / ( 1.0 * size(classMethods[c] - testMethods) ) > | c <- classes(model), size(classMethods[c] - testMethods) > 0 };
  
  println( "Class coverage:" );
  for ( c <- classCoverage ) {
    print( c[0] );
    print( ": " );
    println( c[1] );
  }
  
  real systemCoverage = ( 1.0 * size( ( reachableMethods & allMethods ) - testMethods) ) / ( 1.0 * size(allMethods - testMethods) );
  println( "System coverage: " + toString( systemCoverage ) );
  println( size( ( reachableMethods & allMethods ) - testMethods) );
  println( size(allMethods - testMethods) );
}
