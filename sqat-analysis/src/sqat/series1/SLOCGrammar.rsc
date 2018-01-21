module sqat::series1::SLOCGrammar

// Grammar that parses comments independently from non-comments
//
// The empty literals ("") are introduced to avoid issues with introduced preceding and terminating whitespace/comments

layout Layout = WhitespaceAndComment* !>> [\ \t];

lexical WhitespaceAndComment
   = [\ \t]
   | SingleLineComment
   | MultilineComment
   ;

lexical SingleLineComment = "//" ![\n\r]* $;

lexical MultilineComment
  = "/*" MultilineCommentChar* "*/"
  ;

lexical MultilineCommentChar
  = ![*]
  | "*" !>> "/"
  ;

// For some reason using "("" | NewLines) FollowedLine* EndLine?" results in Ambiguity
start syntax SLOCGrammar
  = ("" | NewLines) FollowedLine+ EndLine?
  | ("" | NewLines) EndLine?
  ;

lexical NewLines = WhitespaceAndComment* {[\n\r] WhitespaceAndComment*}+ !>> [\n\r];

syntax EndLine
  = Line "" $ !>> [\n\r]
  ;

syntax FollowedLine
  = Line [\n\r]+ !>> [\n\r]
  ;

syntax Line
  = LineChar+
  ;

lexical LineChar
  = ![/\ \t\n\r]
  | "/" !>> [*/]
  ;
  