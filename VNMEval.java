public class VNMEval implements VNMVisitor {

    /* =======================
       Small helper utilities
       ======================= */

    // In my evaluator I pass things around as raw Objects, so I made tiny
    // helper casts to avoid repeating (Integer)obj and (Boolean)obj everywhere.
    private int asInt(Object o) {
        return ((Integer) o).intValue();
    }

    private boolean asBool(Object o) {
        return ((Boolean) o).booleanValue();
    }

    // The parser stores string literals with quotes included.
    // Here I strip off the leading and trailing quote characters.
    private String stripQuotes(String s) {
        if (s != null && s.length() >= 2 && s.startsWith("\"") && s.endsWith("\"")) {
            return s.substring(1, s.length() - 1);
        }
        return s;
    }

    // This is my generic "walk all children" helper.
    // Any node that doesn't have special behavior just uses this.
    private Object defaultVisit(SimpleNode node, Object data) throws Exception {
        Object lastResult = null;
        int numKids = node.jjtGetNumChildren();

        // I visit children left-to-right and remember the value of the last one.
        for (int i = 0; i < numKids; i++) {
            lastResult = node.jjtGetChild(i).jjtAccept(this, null);
        }

        // For things like a block/body, I don't need the result, but I keep
        // this return to be consistent with the Visitor API.
        return lastResult;
    }

    /* =======================
       GENERIC / STRUCTURAL NODES
       ======================= */

    // For the "plain" SimpleNode, I just recurse on children.
    public Object visit(SimpleNode n, Object d) throws Exception { 
        return defaultVisit(n, d); 
    }

    public Object visit(ASTbody n, Object d) throws Exception { 
        return defaultVisit(n, d); 
    }

    public Object visit(ASTclause n, Object d) throws Exception { 
        return defaultVisit(n, d); 
    }

    // For variable declarations and function declarations, I’m not actually
    // doing any runtime work yet, so I just return null.
    public Object visit(ASTvar_decl n, Object d) throws Exception { 
        return null; 
    }

    public Object visit(ASTfn_decl n, Object d) throws Exception { 
        return null; 
    }

    public Object visit(ASTident_list n, Object d) throws Exception { 
        return null; 
    }

    public Object visit(ASTfn_call n, Object d) throws Exception { 
        return null; 
    }

    public Object visit(ASTboolean_call n, Object d) throws Exception { 
        return null; 
    }

    public Object visit(ASTexp_list n, Object d) throws Exception { 
        return defaultVisit(n, d); 
    }

    public Object visit(ASTcondition_list n, Object d) throws Exception { 
        return defaultVisit(n, d); 
    }

    /* =======================
       PRINT / PRINTLN
       ======================= */

    // PRINT just evaluates each child expression and prints them in order,
    // staying on the same line.
    public Object visit(ASTPrint n, Object d) throws Exception {
        int childCount = n.jjtGetNumChildren();

        for (int i = 0; i < childCount; i++) {
            Object value = n.jjtGetChild(i).jjtAccept(this, null);
            if (value != null) {
                System.out.print(value.toString());
            }
        }
        return null;
    }

    // PRINTLN behaves exactly like PRINT, but at the end I add a newline.
    public Object visit(ASTPrint_ln n, Object d) throws Exception {
        int childCount = n.jjtGetNumChildren();

        for (int i = 0; i < childCount; i++) {
            Object value = n.jjtGetChild(i).jjtAccept(this, null);
            if (value != null) {
                System.out.print(value.toString());
            }
        }

        // This is the only difference from ASTPrint: I append a newline.
        System.out.println();
        return null;
    }

    /* =======================
       ASSIGNMENT (unused)
       ======================= */

    // The grammar has Assign nodes, but in this simplified evaluator
    // I'm not actually storing any variables, so I just ignore them.
    public Object visit(ASTAssign n, Object d) throws Exception { 
        return null; 
    }

    /* =======================
       IF / ELIF / ELSE
       ======================= */

    // The ASTIf node is structured as:
    //   child 0: condition
    //   child 1: "then" clause
    //   child 2: optional "else / elif" part
    public Object visit(ASTIf n, Object d) throws Exception {
        boolean conditionResult = asBool(n.jjtGetChild(0).jjtAccept(this, null));

        if (conditionResult) {
            // If the condition is true, I evaluate the "then" body.
            n.jjtGetChild(1).jjtAccept(this, null);
        } else if (n.jjtGetNumChildren() > 2) {
            // Otherwise, if there is an else/elif part, I evaluate that.
            n.jjtGetChild(2).jjtAccept(this, null);
        }

        return null;
    }

    // The NULL node represents an empty else branch (#NULL in the grammar),
    // so there's simply nothing to do at evaluation time.
    public Object visit(ASTNULL n, Object d) throws Exception { 
        return null; 
    }

    /* =======================
       BOOLEAN LOGIC
       ======================= */

    // Logical OR:
    // I short-circuit by evaluating the left child first. If it's true,
    // I don't need to look at the right side.
    public Object visit(ASTor n, Object d) throws Exception {
        boolean left = asBool(n.jjtGetChild(0).jjtAccept(this, null));
        if (left) {
            return Boolean.TRUE;
        }
        boolean right = asBool(n.jjtGetChild(1).jjtAccept(this, null));
        return Boolean.valueOf(right);
    }

    // Logical AND:
    // I also short-circuit here. If the left side is false,
    // there's no reason to evaluate the right side.
    public Object visit(ASTand n, Object d) throws Exception {
        boolean left = asBool(n.jjtGetChild(0).jjtAccept(this, null));
        if (!left) {
            return Boolean.FALSE;
        }
        boolean right = asBool(n.jjtGetChild(1).jjtAccept(this, null));
        return Boolean.valueOf(right);
    }

    // Logical NOT:
    // Just flips the boolean value of its single child.
    public Object visit(ASTnot n, Object d) throws Exception {
        boolean inner = asBool(n.jjtGetChild(0).jjtAccept(this, null));
        return Boolean.valueOf(!inner);
    }

    /* =======================
       COMPARISONS
       ======================= */

    // Generic comparison node: the shape is
    //   child 0: left expression  (L)
    //   child 1: comparator node  (<, <=, ==, etc.)
    //   child 2: right expression (R)
    public Object visit(ASTcomparison n, Object d) throws Exception {
        int leftValue  = asInt(n.jjtGetChild(0).jjtAccept(this, null));
        int rightValue = asInt(n.jjtGetChild(2).jjtAccept(this, null));

        SimpleNode operatorNode = (SimpleNode) n.jjtGetChild(1);

        // I test which specific comparator node this is and return the right boolean.
        if (operatorNode instanceof ASTle) {
            return Boolean.valueOf(leftValue < rightValue);
        }
        if (operatorNode instanceof ASTleq) {
            return Boolean.valueOf(leftValue <= rightValue);
        }
        if (operatorNode instanceof ASTgre) {
            return Boolean.valueOf(leftValue > rightValue);
        }
        if (operatorNode instanceof ASTgeq) {
            return Boolean.valueOf(leftValue >= rightValue);
        }
        if (operatorNode instanceof ASTeq) {
            return Boolean.valueOf(leftValue == rightValue);
        }
        if (operatorNode instanceof ASTneq) {
            return Boolean.valueOf(leftValue != rightValue);
        }

        // If I ever hit a comparator I don't recognize, I play it safe and return false.
        return Boolean.FALSE;
    }

    // The individual comparator nodes themselves don't evaluate to any direct value.
    // They're only used as "tags" inside ASTcomparison.
    public Object visit(ASTle n, Object d)   throws Exception { return null; }
    public Object visit(ASTleq n, Object d)  throws Exception { return null; }
    public Object visit(ASTgre n, Object d)  throws Exception { return null; }
    public Object visit(ASTgeq n, Object d)  throws Exception { return null; }
    public Object visit(ASTeq n, Object d)   throws Exception { return null; }
    public Object visit(ASTneq n, Object d)  throws Exception { return null; }
    public Object visit(ASTin n, Object d)   throws Exception { return null; }
    public Object visit(ASTnotin n, Object d)throws Exception { return null; }

    /* =======================
       ARITHMETIC EXPRESSIONS
       ======================= */

    // ASTsum represents a sequence of terms being added together.
    // The first child is the base, and the rest are added on top.
    public Object visit(ASTsum n, Object d) throws Exception {
        int numChildren = n.jjtGetNumChildren();
        int result = asInt(n.jjtGetChild(0).jjtAccept(this, null));

        for (int i = 1; i < numChildren; i++) {
            result += asInt(n.jjtGetChild(i).jjtAccept(this, null));
        }

        return Integer.valueOf(result);
    }

    // ASTpos is essentially a no-op unary plus.
    // I just return whatever the child evaluates to.
    public Object visit(ASTpos n, Object d) throws Exception {
        return n.jjtGetChild(0).jjtAccept(this, null);
    }

    // ASTneg is unary minus: it negates the integer value of its child.
    public Object visit(ASTneg n, Object d) throws Exception {
        int value = asInt(n.jjtGetChild(0).jjtAccept(this, null));
        return Integer.valueOf(-value);
    }

    // ASTmul represents chained multiplication.
    // Like sum, the first child is the base and I multiply by each subsequent child.
    public Object visit(ASTmul n, Object d) throws Exception {
        int numChildren = n.jjtGetNumChildren();
        int product = asInt(n.jjtGetChild(0).jjtAccept(this, null));

        for (int i = 1; i < numChildren; i++) {
            product *= asInt(n.jjtGetChild(i).jjtAccept(this, null));
        }

        return Integer.valueOf(product);
    }

    // ASTdiv does chained integer division, left-associative.
    public Object visit(ASTdiv n, Object d) throws Exception {
        int numChildren = n.jjtGetNumChildren();
        int quotient = asInt(n.jjtGetChild(0).jjtAccept(this, null));

        for (int i = 1; i < numChildren; i++) {
            quotient /= asInt(n.jjtGetChild(i).jjtAccept(this, null));
        }

        return Integer.valueOf(quotient);
    }

    // ASTmod is modulus; here I only use two children: L % R.
    public Object visit(ASTmod n, Object d) throws Exception {
        int left  = asInt(n.jjtGetChild(0).jjtAccept(this, null));
        int right = asInt(n.jjtGetChild(1).jjtAccept(this, null));
        return Integer.valueOf(left % right);
    }

    /* =======================
       LITERALS
       ======================= */

    // A numeric literal is stored in the AST node's value as a String.
    // I parse it to an int and then wrap it as an Integer.
    public Object visit(ASTnumber n, Object d) throws Exception {
        String text = (String) n.jjtGetValue();
        return Integer.valueOf(Integer.parseInt(text));
    }

    // A string literal includes quotes; I remove them with stripQuotes().
    public Object visit(ASTstring n, Object d) throws Exception {
        String raw = (String) n.jjtGetValue();
        return stripQuotes(raw);
    }

    // For RETURN, I just walk its children in case there is an expression.
    // The actual "return handling" is not implemented here, but this allows
    // me to reuse the same visitor for evaluation.
    public Object visit(ASTReturn node, Object data) throws Exception {
        return defaultVisit(node, data);
    }

    // Boolean literals map directly to Java Boolean constants.
    public Object visit(ASTTRUE n, Object d) throws Exception { 
        return Boolean.TRUE; 
    }

    public Object visit(ASTFALSE n, Object d) throws Exception { 
        return Boolean.FALSE; 
    }

    /* =======================
       MISC / PLACEHOLDER NODES
       ======================= */

    // Identifier nodes and vector-related nodes exist in the grammar,
    // but in this simplified evaluator I don't actually resolve them
    // to any stored values yet, so they just return null.

    public Object visit(ASTidnum n, Object d) throws Exception { 
        return null; 
    }

    public Object visit(ASTidbool n, Object d) throws Exception { 
        return null; 
    }

    public Object visit(ASTidvec n, Object d) throws Exception { 
        return null; 
    }

    public Object visit(ASTvec_const n, Object d) throws Exception { 
        return null; 
    }

    // Similarly, FOR and WHILE structures are present in the AST,
    // but I'm not interpreting them here; they are placeholders.
    public Object visit(ASTFor n, Object d) throws Exception { 
        return null; 
    }

    public Object visit(ASTWhile n, Object d) throws Exception { 
        return null; 
    }
}
