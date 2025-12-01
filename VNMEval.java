YonkoOzy
ozydrag
0.1%

Wise one 🧠 — 2024-04-27 7:53 PM
no one cares about you
fuck u? — 2024-10-03 11:11 PM
@Wise one 🧠
Image
Wise one 🧠 — 2024-10-03 11:18 PM
thanks man
superstar — 2024-10-04 7:17 AM
Ayesha that’s so sad😭
😭
😭
😭
😭
😭
Why didn’t you switch to the other guys section
BiTcHlEsS — 2024-10-04 3:19 PM
Seeing that you got Khalid and still not switching is something I won’t understand
superstar — 2024-10-10 9:04 PM
Image
superstar — 2024-10-10 9:24 PM
Image
BiTcHlEsS — 2025-09-25 10:57 AM
Attachment file type: acrobat
CPS843_Assignment_1.pdf
7.27 MB
Attachment file type: archive
843-Quizzes.zip
2.41 MB
Attachment file type: acrobat
Assignment2_Andrew_Le_501090188.pdf
722.39 KB
superstar — 2025-09-30 5:37 PM
https://www.youtube.com/watch?v=OIKL6wFjFOo&ab_channel=NesoAcademy
YouTube
Neso Academy
Introduction to Parsers
Image
https://www.youtube.com/watch?v=wQjppolFdas&ab_channel=NesoAcademy
YouTube
Neso Academy
Ambiguous Grammar
Image
superstar — 2025-10-31 7:14 PM
https://drive.google.com/drive/u/1/folders/1uIOp7F3L2yj4dRl37f30XNTkUA7rAY-J
Google Drive
^768 project
Image
@Wise one 🧠
did your 768 project work
can you call me
superstar — 2025-11-10 7:15 PM
Project 3
options {
  IGNORE_CASE=false;
  MULTI=true;
  JJTREE_OUTPUT_DIRECTORY="AST";
  VISITOR=true;
  NODE_DEFAULT_VOID=true;
Expand
message.txt
9 KB
superstar
 pinned a message to this channel. See all pinned messages. — 2025-11-10 7:15 PM
superstar — 2025-11-10 7:23 PM
chmod +x run t
chmod +x runtests
superstar — 2025-11-12 10:22 AM
NOO BRO HE CHANGES SHIT
superstar — 2025-11-12 11:38 AM
Okay i think it should still be fine
Image
BiTcHlEsS — 2025-11-22 12:50 PM
Image
looks like it still works
superstar — 2025-11-22 12:52 PM
Okay perfect thank you
BiTcHlEsS — 2025-11-22 1:09 PM
idk how much i can change this up tbh
fuck u? — 2025-11-22 1:10 PM
Spacing and comments ?
BiTcHlEsS — 2025-11-22 1:11 PM
yeah thats pretty much what i did
superstar — 2025-11-22 1:11 PM
Wdym just gpt it no
BiTcHlEsS — 2025-11-22 1:12 PM
you cant change the actual var names cuz they gave us them
superstar — 2025-11-22 1:12 PM
Structure of the code
Move stuff around
fake ahraz — 2025-11-23 7:09 PM
options {
  IGNORE_CASE=false;
  MULTI=true;
  JJTREE_OUTPUT_DIRECTORY="AST";
  VISITOR=true;
  // standard options setup
Expand
message.txt
7 KB
superstar — 2025-11-25 11:49 AM
ill do project 4
superstar — 2025-11-25 2:44 PM
LAB IS DONE GANGY
Image
VNM.jjt
options {
  IGNORE_CASE=false;
  MULTI=true;	// This will generate one AST class for each non-suppressed non-terminal
  JJTREE_OUTPUT_DIRECTORY="AST";  // This will put all your AST classes in the AST directory
  VISITOR=true;	// This won't be used until the next assignment, but will be needed to make your assignment compile properly
  VISITOR_EXCEPTION="Exception";
Expand
message.txt
8 KB
VNMEval.java
public class VNMEval implements VNMVisitor {

    /* Utility helpers */
    private int asInt(Object o) { return ((Integer)o).intValue(); }
    private boolean asBool(Object o) { return ((Boolean)o).booleanValue(); }
Expand
message.txt
8 KB
Image
superstar — 2025-11-25 7:07 PM
#include <stdio.h>
#include <netdb.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <stdlib.h>
Expand
peer.c
11 KB
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h> 
#include <stdlib.h>
#include <string.h>
#include <netdb.h> 
Expand
server.c
9 KB
@BiTcHlEsS  you can use this to submit
@BookBoy
fuck u? — 2025-11-25 7:15 PM
@BookBoy can you please review the codes and learn them especially the peer file
BookBoy — 2025-11-25 10:30 PM
Ok
Js for u
I will
Cus u asked nucely
superstar — 2025-11-25 10:50 PM
Image
BiTcHlEsS — Yesterday at 1:51 PM
Image
BookBoy — 7:08 PM
options {
  IGNORE_CASE=false;
  MULTI=true;	// This will generate one AST class for each non-suppressed non-terminal
  JJTREE_OUTPUT_DIRECTORY="AST";  // This will put all your AST classes in the AST directory
  VISITOR=true;	// This won't be used until the next assignment, but will be needed to make your assignment compile properly
  VISITOR_EXCEPTION="Exception";
Expand
message.txt
9 KB
ignore this^
﻿
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
message.txt
8 KB