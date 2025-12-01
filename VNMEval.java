public class VNMEval implements VNMVisitor {

    /* Utility helpers */
    private int asInt(Object o) { return ((Integer)o).intValue(); }
    private boolean asBool(Object o) { return ((Boolean)o).booleanValue(); }

    private String stripQuotes(String s) {
        if (s.startsWith("\"") && s.endsWith("\""))
            return s.substring(1, s.length() - 1);
        return s;
    }

    public Object defaultVisit(SimpleNode node, Object data) throws Exception {
        Object result = null;
        for (int i = 0; i < node.jjtGetNumChildren(); i++)
            result = node.jjtGetChild(i).jjtAccept(this, null);
        return result;
    }

    /* GENERIC NODES */
    public Object visit(SimpleNode n, Object d) throws Exception { return defaultVisit(n,d); }
    public Object visit(ASTbody n, Object d) throws Exception { return defaultVisit(n,d); }
    public Object visit(ASTclause n, Object d) throws Exception { return defaultVisit(n,d); }
    public Object visit(ASTvar_decl n, Object d) throws Exception { return null; }
    public Object visit(ASTfn_decl n, Object d) throws Exception { return null; }
    public Object visit(ASTident_list n, Object d) throws Exception { return null; }
    public Object visit(ASTfn_call n, Object d) throws Exception { return null; }
    public Object visit(ASTboolean_call n, Object d) throws Exception { return null; }
    public Object visit(ASTexp_list n, Object d) throws Exception { return defaultVisit(n,d); }
    public Object visit(ASTcondition_list n, Object d) throws Exception { return defaultVisit(n,d); }

    /* PRINT */
    public Object visit(ASTPrint n, Object d) throws Exception {
        for (int i = 0; i < n.jjtGetNumChildren(); i++) {
            Object v = n.jjtGetChild(i).jjtAccept(this, null);
            if (v != null) System.out.print(v.toString());
        }
        return null;
    }

    public Object visit(ASTPrint_ln n, Object d) throws Exception {
        for (int i = 0; i < n.jjtGetNumChildren(); i++) {
            Object v = n.jjtGetChild(i).jjtAccept(this, null);
            if (v != null) System.out.print(v.toString());
        }
        System.out.println();
        return null;
    }

    /* ASSIGN — Not used */
    public Object visit(ASTAssign n, Object d) throws Exception { return null; }

    /* IF / ELIF / ELSE */
    public Object visit(ASTIf n, Object d) throws Exception {
        boolean cond = asBool(n.jjtGetChild(0).jjtAccept(this, null));
        if (cond) {
            n.jjtGetChild(1).jjtAccept(this, null);
        } else if (n.jjtGetNumChildren() > 2) {
            n.jjtGetChild(2).jjtAccept(this, null);
        }
        return null;
    }

    public Object visit(ASTNULL n, Object d) throws Exception { return null; }

    /* BOOLEAN LOGIC */
    public Object visit(ASTor n, Object d) throws Exception {
        boolean left = asBool(n.jjtGetChild(0).jjtAccept(this, null));
        if (left) return Boolean.TRUE;
        return Boolean.valueOf(asBool(n.jjtGetChild(1).jjtAccept(this, null)));
    }

    public Object visit(ASTand n, Object d) throws Exception {
        boolean left = asBool(n.jjtGetChild(0).jjtAccept(this, null));
        if (!left) return Boolean.FALSE;
        return Boolean.valueOf(asBool(n.jjtGetChild(1).jjtAccept(this, null)));
    }

    public Object visit(ASTnot n, Object d) throws Exception {
        return Boolean.valueOf(!asBool(n.jjtGetChild(0).jjtAccept(this, null)));
    }

    /* COMPARISONS */
    public Object visit(ASTcomparison n, Object d) throws Exception {
        int L = asInt(n.jjtGetChild(0).jjtAccept(this, null));
        int R = asInt(n.jjtGetChild(2).jjtAccept(this, null));

        SimpleNode op = (SimpleNode)n.jjtGetChild(1);

        if (op instanceof ASTle)  return Boolean.valueOf(L < R);
        if (op instanceof ASTleq) return Boolean.valueOf(L <= R);
        if (op instanceof ASTgre) return Boolean.valueOf(L > R);
        if (op instanceof ASTgeq) return Boolean.valueOf(L >= R);
        if (op instanceof ASTeq)  return Boolean.valueOf(L == R);
        if (op instanceof ASTneq) return Boolean.valueOf(L != R);

        return Boolean.FALSE;
    }

    public Object visit(ASTle n,Object d)throws Exception { return null; }
    public Object visit(ASTleq n,Object d)throws Exception { return null; }
    public Object visit(ASTgre n,Object d)throws Exception { return null; }
    public Object visit(ASTgeq n,Object d)throws Exception { return null; }
    public Object visit(ASTeq n,Object d)throws Exception { return null; }
    public Object visit(ASTneq n,Object d)throws Exception { return null; }
    public Object visit(ASTin n,Object d)throws Exception { return null; }
    public Object visit(ASTnotin n,Object d)throws Exception { return null; }

    /* ARITHMETIC */
    public Object visit(ASTsum n, Object d) throws Exception {
        int total = asInt(n.jjtGetChild(0).jjtAccept(this, null));
        for (int i = 1; i < n.jjtGetNumChildren(); i++)
            total += asInt(n.jjtGetChild(i).jjtAccept(this, null));
        return Integer.valueOf(total);
    }

    public Object visit(ASTpos n, Object d) throws Exception {
        return n.jjtGetChild(0).jjtAccept(this, null);
    }

    public Object visit(ASTneg n, Object d) throws Exception {
        return Integer.valueOf(-asInt(n.jjtGetChild(0).jjtAccept(this, null)));
    }

    public Object visit(ASTmul n, Object d) throws Exception {
        int total = asInt(n.jjtGetChild(0).jjtAccept(this, null));
        for (int i = 1; i < n.jjtGetNumChildren(); i++)
            total *= asInt(n.jjtGetChild(i).jjtAccept(this, null));
        return Integer.valueOf(total);
    }

    public Object visit(ASTdiv n, Object d) throws Exception {
        int total = asInt(n.jjtGetChild(0).jjtAccept(this, null));
        for (int i = 1; i < n.jjtGetNumChildren(); i++)
            total /= asInt(n.jjtGetChild(i).jjtAccept(this, null));
        return Integer.valueOf(total);
    }

    public Object visit(ASTmod n, Object d) throws Exception {
        int L = asInt(n.jjtGetChild(0).jjtAccept(this, null));
        int R = asInt(n.jjtGetChild(1).jjtAccept(this, null));
        return Integer.valueOf(L % R);
    }

    /* LITERALS */
    public Object visit(ASTnumber n, Object d) throws Exception {
        return Integer.valueOf(Integer.parseInt((String)n.jjtGetValue()));
    }

    public Object visit(ASTstring n, Object d) throws Exception {
        return stripQuotes((String)n.jjtGetValue());
    }

    public Object visit(ASTReturn node, Object data) throws Exception {
        return defaultVisit(node, data);
    }

    public Object visit(ASTTRUE n, Object d) throws Exception { return Boolean.TRUE; }
    public Object visit(ASTFALSE n, Object d) throws Exception { return Boolean.FALSE; }

    /* MISC required nodes */
    public Object visit(ASTidnum n,Object d)throws Exception{ return null; }
    public Object visit(ASTidbool n,Object d)throws Exception{ return null; }
    public Object visit(ASTidvec n,Object d)throws Exception{ return null; }
    public Object visit(ASTvec_const n,Object d)throws Exception{ return null; }

    public Object visit(ASTFor n,Object d)throws Exception{ return null; }
    public Object visit(ASTWhile n,Object d)throws Exception{ return null; }
}