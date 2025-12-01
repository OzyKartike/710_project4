options {
  IGNORE_CASE=false;
  MULTI=true;	// This will generate one AST class for each non-suppressed non-terminal
  JJTREE_OUTPUT_DIRECTORY="AST";  // This will put all your AST classes in the AST directory
  VISITOR=true;	// This won't be used until the next assignment, but will be needed to make your assignment compile properly
  VISITOR_EXCEPTION="Exception";
}

PARSER_BEGIN(VNM)

public class VNM {
}

PARSER_END(VNM)

TOKEN_MGR_DECLS : 
{

}

SKIP : {
  " "     
| "\t"    
| "\n"    
| "\r" 
}

TOKEN : 
{
  < LESS: "<">
| < LE:     "<="  >
| < GREAT:  ">"   >
| < GE:     ">="  >
| < EQ:     "=="  >
| < NEQ:    "!="  >
| < IN:     "=in" >
| < NOTIN:  "!in" >
| < PLUS:	  "+"   >
| < MINUS:	"-"   >
| < TIMES:	"*"   >
| < DIV:	  "/"   >
| < AND:	  "&"   >
| < VBAR:	  "|"   >
| < NOT:	  "!"   >
| < ASSGN:	":="   >
| < LRND:	  "("   >
| < LSQU:	  "["   >
| < RRND:	  ")"   >
| < RSQU:	  "]"   >
| < COMMA:	","   >
| < SCOL:	  ";"   >
| < TRUE:	  "#1"  >
| < FALSE:	"#0"  >
| < RANGE:	".."  >
}

TOKEN [IGNORE_CASE]:
{
  < DO:"DO">
| < WHILE:"WHILE">
| < FOR:"FOR">
| < IF:"IF">
| < THEN:"THEN">
| < ELIF:"ELIF">
| < ELSE:"ELSE">
| < FI:"FI">
| < FUNCTION:"FUNCTION">
| < RETURN:"RETURN">
| < END:"END">
| < PRINT:"PRINT">
| < PRINTLN:"PRINTLN">
| < VAR:"VAR">
}

TOKEN : 
{
  < #DIGIT:	["0"-"9"]>
| < #LOWER:	["a"-"z"]>
| < #UPPER:	["A"-"Z"]>
| < #LETTER: ["a"-"z","A"-"Z"]>

// string / number / ids
| < STRING: "\"" (~["\""])* "\"" > 
| < NUMBER:	(<DIGIT>)+ >
| < IDNUM:  "#" <LETTER>(<LETTER>|<DIGIT>)* >  
| < IDBOOL:	"?" <LETTER>(<LETTER>|<DIGIT>)* >
| < IDVEC:  "v_" (<LETTER>|<DIGIT>)+ >  
}

// Special tokens are saved, but not sent to the parser
SPECIAL_TOKEN : 
{
  <COMMENT_SINGLE: "//" (~["\n","\r"])* ("\n"|"\r"|"\r\n")>
}



// ==================================================
//                  PARSER BEGINS
// ==================================================

SimpleNode start	() #void :
{}
{  S()  { return (SimpleNode) (jjtree.popNode()); }
| < EOF > {throw new ParseException("End of File.");}
}

// entry point to parser. Called by TestVNM.java
void S() throws ParseException	#void	 :
{}
{	statement_LL1() ";"
| LOOKAHEAD(identifier() ":=") assign_stat() ";" 
| expression() ";" 
| boolean_call() ";"
}

//------------------------    STATEMENTS, BODIES AND CLAUSES -----------------------------------

// These are all the statements which can be differentiated from each other 
// with a single lookahead
void statement_LL1()	#void	 :
{}
{	var_decl()
| fn_decl()
| return_stat()
| print_stat()
| println_stat()
| if_stat()
| for_stat()
| while_stat()
}

// These are the statements not at the top level, i.e. inside bodies or clauses
// Maintain structural integrity of assignments and function calls by not factoring them
// and using a lookahead to distinguish between them

void statement()	#void :
{}
{	statement_LL1()
| LOOKAHEAD(2) fn_call()
| assign_stat()
}

void body()		 :
{}
{	(statement() ";")*
}

void clause()		 :
{}
{	(statement() ";")+
// Note: clause requires at least one statement, body allows zero or more.
}

//---------------------------   DECLARATIONS ------------------------------------------------

void var_decl()	#void :
{}
{	<VAR> var_list()
}

void var_list()	#void :
{}
{	(identifier() ("," identifier())*) #var_decl
}

void fn_decl()		 :
{}
{	<FUNCTION> identifier() "(" (ident_list())? ")" body() <END>
}

void ident_list()	 :
{}
{	identifier() ("," identifier())*
}


//---------------------------   FUNCTION CALLS AND RETURNS ----------------------------------

// Separate out function calls that can be used in expressions (numbers and vectors),
// from those used in conditions (boolean).
void fn_call()		 :
{}
{	idnum() "(" (exp_list())? ")"
|	idvec() "(" (exp_list())? ")"
}

void boolean_call()		 :
{}
{	idbool() "(" (exp_list())? ")"
}

void exp_list()	 :
{}
{	expression() ("," expression())*
}

// Since we split function calls by boolean and numerical,
// we need an explicit condition list
void condition_list() :
{}
{ condition() ("," condition())*
}

void return_stat()	#Return :
{}
{	<RETURN> returnval()
}

// syntactic lookahead to distinguish conditions and expressions.
void returnval()	#void :
{}
{	LOOKAHEAD(condition()) condition()
| expression() 
}

void print_stat() #Print :
{}
{	<PRINT> print_list()
}

void println_stat() #Print_ln :
{}
{ <PRINTLN> (print_list())?
}

// *** FIXED: allow bool_simple (TRUE/FALSE) in print_list ***
// In the original version of this language, PRINT could only handle expressions,
// strings, and boolean identifiers. In this lab, I wanted to also be able to
// print raw boolean literals (#1 / #0). To make that work, I added bool_simple()
// into the allowed alternatives. This keeps the overall structure the same
// (a comma-separated list), but slightly widens what each element can be.
void print_list() #void :
{}
{
  // First printable item
  (
    expression()
  | string()
  | idbool()
  | bool_simple()     // my addition: this includes TRUE/FALSE tokens as well
  )
  // Zero or more ", <item>" after
  (
    ","
    (
      expression()
    | string()
    | idbool()
    | bool_simple()   // same extended set for subsequent items
    )
  )*
}

void assign_stat() #Assign :
{}
{ idnum() ":=" expression()
| idbool() ":=" condition()
| idvec() ":=" vec_const()
}

void if_stat()		#If :
{}
{	<IF> condition() <THEN> clause() else_clause() <FI>
}

void else_clause	() #void:
{}
{	 (<ELIF> condition() <THEN> clause() else_clause()) #If
|  <ELSE> clause()
|  {} #NULL
}

void for_stat()	#For :
{}
{	<FOR> idnum() <IN> exp_list() <DO> body() <END>
}

void while_stat() #While	 :
{}
{	<WHILE> condition() <DO> body() <END>
}


//---------------------------   CONDITIONS ---------------------------------------------------

void condition()	#void :
{}
{	(and_clause() ("|" and_clause())*) #or(>1)
}

void and_clause()	#void :
{}
{	(not_clause() ("&" not_clause())*) #and(>1)
}

// syntactic lookahead to distinguish comparisons and conditions.
void not_clause()	#void :
{}
{	"!" not_clause()  #not
| LOOKAHEAD(expression() comparator()) comparison() 
| "(" condition() ")"
| LOOKAHEAD(2) boolean_call()
| bool_simple()
}

void comparison()	 :
{}
{	expression() comparator() expression()
}

void comparator()	 #void :
{}
{	"<"   #le
| "<="  #leq
| ">"   #gre
| ">="  #geq
| "=="  #eq
| "!="  #neq
| "=in" #in
| "!in" #notin
}


//---------------------------   EXPRESSIONS ------------------------------------------------

void expression()	#void	 :
{}
{	("+" product() (summand())*) #sum(>1)
|	(neg() (summand())*)         #sum
|	(product() (summand())*)     #sum(>1)
}

void neg() :
{}
{	"-" product() 
}

void summand() #void :
{}
{	"+" product() #pos
|	"-" product() #neg
}
 
void product() #void :
{}
{	term() moreterms()
}

void moreterms() #void :
{}
{	"*" term() #mul(2) moreterms()
|	"/" term() #div(2) moreterms()
|	"%" term() #mod(2) moreterms()
| {}
}

// use a lookahead to distinguish fn_call from simple_term.
void term()		#void :
{}
{	"(" expression() ")"
| LOOKAHEAD(2) fn_call()
| simple_term()
}

void simple_term()	#void :
{}
{	idnum()
| idvec()
| number()
| vec_const() 
}

// vectors & vector booleans
void vec_const()	 :
{}
{	
  LOOKAHEAD("[" expression()) "[" (exp_list())? "]"
| "[" (condition_list())? "]"
}

void identifier()	#void :
{}
{	idnum()
| idbool()
| idvec() 
}

void bool_simple()	#void	 :
{}
{	idbool() 
| <TRUE>  #TRUE
| <FALSE> #FALSE
}   


//---------------------------   TERMINALS WITH VALUES  --------------------------------------

// *** FIXED: use t.image instead of t.getValue() everywhere ***
// In the original skeleton, the terminals used t.getValue(), which is usually
// null unless I explicitly populate it. For this assignment, I actually want
// the raw text of the token (like "#x1", "?flag", "42", or "\"hi\"") to be
// stored on each AST node. JavaCC always fills t.image with the lexeme, so
// here I'm explicitly saving t.image into the JJTree node's value.

void idvec ()  :
{
  Token t;
}
{
  t = <IDVEC>
  {
    // I store the exact name of the vector variable, e.g., "v_x1".
    jjtThis.jjtSetValue(t.image);
  }
}

void idnum () :
{
  Token t;
}
{
  t = <IDNUM>
  {
    // Same idea for numeric identifiers like "#count".
    // Later, the evaluator can look at this string to know which variable it is.
    jjtThis.jjtSetValue(t.image);
  }
}  

void idbool ()  :
{
  Token t;
}
{
  t = <IDBOOL>
  {
    // Boolean identifiers, such as "?flag", also keep their spelling here.
    jjtThis.jjtSetValue(t.image);
  }
}

void number () :
{
  Token t;
}
{
  t = <NUMBER>
  {
    // For numbers, I keep the literal digits as a string.
    // The evaluator can parse this to an integer when it needs to.
    jjtThis.jjtSetValue(t.image);
  }
}

void string () :
{
  Token t;
}
{
  t = <STRING>
  {
    // String literals are stored with quotes in the token image.
    // I keep that here; later, in the evaluator, I can strip the quotes.
    jjtThis.jjtSetValue(t.image);
  }
}
