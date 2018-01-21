module sqat::series1::A1_SLOC

import IO;
import ParseTree;
import String;
import util::FileSystem;
import util::Math;
import Set;
import Map;
import sqat::series1::SLOCGrammar;

/* 

Count Source Lines of Code (SLOC) per file:
- ignore comments
- ignore empty lines

Tips
- use locations with the project scheme: e.g. |project:///jpacman/...|
- functions to crawl directories can be found in util::FileSystem
- use the functions in IO to read source files

Answer the following questions:
- what is the biggest file in JPacman?
- what is the total size of JPacman?
- is JPacman large according to SIG maintainability?
- what is the ratio between actual code and test code size?
 : All answers are in the main()

Sanity checks:
- write tests to ensure you are correctly skipping multi-line comments
- and to ensure that consecutive newlines are counted as one.
- compare you results to external tools sloc and/or cloc.pl

Sanity checks Answers:
- Check
- Check
- Exactly equal to sloc

Bonus:
- write a hierarchical tree map visualization using vis::Figure and 
  vis::Render quickly see where the large files are. 
  (https://en.wikipedia.org/wiki/Treemapping) 

*/

alias SLOC = map[loc file, int sloc];

SLOC sloc(loc project)
  = ( f : sloc( readFile( f ) ) | f <- find( project, "java" ) );

SLOC jpacmanSloc( )
  = sloc( |project://jpacman/| );

SLOC jpacmanMainSloc( )
  = sloc( |project://jpacman/src/main/java/| );

SLOC jpacmanTestSloc( )
  = sloc( |project://jpacman/src/test/java/| );

int sloc(str content) {
  SLOCGrammar g = parse(#SLOCGrammar,content);
  int numLines = 0;
  visit ( g ) {
    case Line l: numLines = numLines + 1;
  }
  return numLines;
}

loc findBiggestFile( SLOC fileSlocs )
  = ( firstLoc | fileSlocs[ l ] > fileSlocs[ it ] ? l : it | loc l <- fileSlocs )
  when firstLoc := toList( domain( fileSlocs ) )[ 0 ];

int sumSlocs(SLOC fileSlocs)
  = ( 0 | it + fileSlocs[ l ] | loc l <- fileSlocs );

void main() {
  slocs = jpacmanSloc( );
  print( "Biggest file: " );
  biggestFile = findBiggestFile( slocs );
  println( biggestFile );
  print( "Biggest file size: " );
  println( slocs[ biggestFile ] );
  println( );
  print( "Total SLOC of JPacman: " );
  println( sumSlocs( slocs ) );
  println( );
  println( "JPacman is extremely small. Any Java program under 66 KLOC is extremely small." );
  println( "Though, there is a difference between SLOCs including or excluding comments" );
  println( "For this case that should not matter, as even if comments were included,");
  println( "it would still be smaller than 66 KSLOCs" );
  println( );
  numMainSlocs = sumSlocs( jpacmanMainSloc( ) );
  numTestSlocs = sumSlocs( jpacmanTestSloc( ) );
  println( "#main SLOCs: " + toString( numMainSlocs ) );
  println( "#test SLOCs: " + toString( numTestSlocs ) );
  println( "main/test ratio: " + toString( toReal(numMainSlocs)/numTestSlocs ) );
}

test bool testEmpty( ) =
  sloc( "" ) == 0;

test bool testEmptyLines( ) =
  sloc( "\n\n\n" ) == 0;

test bool testEmptyCommentLines( ) =
  sloc( "\n\n/* test */\n// comment\n/* unknown */\n" ) == 0;

test bool testStatementLines( ) =
  sloc( "import;
        '
        'run( )
        '/* multi line
        '   comments */
        'run( );
        '" ) == 3;