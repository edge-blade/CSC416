package slpinterpreter;
import java.lang.Math;

class interp {
//generates a table to successfully begin interpreting the statement "prog#"
    static void interp(stm s) { /* you write this part */
        maxArgs(s);
        Table t1 = interpStm(s,null);
    }
    //maxArgs run with a statement to break apart into subclasses of statements
    static int maxArgs(stm s) { /* you write this part */
        if(s instanceof CompoundStm){
            return Math.max(maxArgs(((CompoundStm)s).stm1), maxArgs(((CompoundStm)s).stm2));
        }
        else if(s instanceof AssignStm){
            return maxArgs(((AssignStm)s).exp);
        }
        else if(s instanceof PrintStm){
            return Math.max(numPrints((((PrintStm)s).exps)),maxArgs(((PrintStm)s).exps));
        }
        return -1;
    }
    //maxArgs run with Expression List to count the number of args within an expression list
    static int maxArgs(ExpList el){
        if(el instanceof PairExpList){
            return  Math.max(maxArgs(((PairExpList)el).head),maxArgs(((PairExpList)el).tail));
        }
        else if (el instanceof LastExpList){
            return maxArgs(((LastExpList)el).head);
        }
        return -1;
    }
    //maxArgs run wtih Expression to branch into subclasses of expression
    static int maxArgs(Exp e){
        if(e instanceof IdExp){
            return 0;
        }
        else if(e instanceof NumExp){
            return 0;
        }
        else if(e instanceof OpExp){
            return Math.max(maxArgs(((OpExp)e).left), maxArgs(((OpExp)e).right));
        }
        else if(e instanceof EseqExp){
            return Math.max(maxArgs(((EseqExp)e).exp), maxArgs(((EseqExp)e).stm));
        }
        return -1;
    }
    //Counts the number of args within a PrintStm
    static int numPrints(ExpList el){
        if(el instanceof PairExpList){
            return 1 + numPrints(((PairExpList)el).tail);
        }
        else if(el instanceof LastExpList){
            return 1;
        }
        return 0;
    }
//main method that runs all programs defined within the prog.java file
    public static void main(String args[]) throws java.io.IOException {
        System.out.println(maxArgs(prog.prog));
        System.out.println(maxArgs(prog.prog2));
        System.out.println(maxArgs(prog.prog3));
        System.out.println(maxArgs(prog.prog4));
        System.out.println(maxArgs(prog.prog5));
        System.out.println(maxArgs(prog.prog6));
        interp(prog.prog6);
    }
    //Interprets the program in terms of reading the data in expressions,
    //returns a result and a Table with modified values
    
   static IntAndTable interpExp(Exp e, Table t){
       if(e instanceof IdExp){
            return new IntAndTable(t.lookUp(((IdExp)e).id),t);
        }
        else if(e instanceof NumExp){
          return new IntAndTable(pullNumExp(e), t);
        }
        else if(e instanceof OpExp){
          return readOp((((OpExp)e).left),(((OpExp)e).oper),(((OpExp)e).right),t);
        }
        else{//not running statement inside EseqExp
            Table nT = interpStm(((EseqExp)e).stm, t);
            return interpExp(((EseqExp)e).exp,nT);
        }
   }
    // analyzes a statement while passing a table
   static Table interpStm(stm s, Table t) { 
        if(s instanceof CompoundStm){
            Table t1 = interpStm(((CompoundStm)s).stm1, t);
           return interpStm(((CompoundStm)s).stm2, t1);
        }
        else if(s instanceof AssignStm){
           IntAndTable IAT = interpExp(((AssignStm)s).exp, t); 
           return new Table((((AssignStm)s).id), IAT.i, IAT.t);
        }
        else{
         return helperPrint(((PrintStm)s).exps, t);
        }
    }
    //Helper function to get a value of of NumExp
    static int pullNumExp(Exp e){
        return ((NumExp)e).num;
    }
    //readOp will interpret the result of a OpExp
   static IntAndTable readOp(Exp l, int Op, Exp r, Table t){//break into smaller cases
            IntAndTable it1 = interpExp(l,t);
            IntAndTable it2 = interpExp(r, it1.t);
        if(Op == 1){
            return new IntAndTable(it1.i + it2.i, it2.t);
        }
        else if(Op == 2){
            return new IntAndTable(it1.i - it2.i, it2.t);
        }
        else if(Op == 3){
            return new IntAndTable(it1.i * it2.i, it2.t);
        }
        else{
            return new IntAndTable(it1.i / it2.i, it2.t);
        }
    }
    //Helper funciton to evaluate the expression list within a PrintStm
 static Table helperPrint(ExpList el, Table t){
        if(el instanceof PairExpList){
            IntAndTable it = interpExp(((PairExpList)el).head,t);
            System.out.print(it.i + " ");
            return helperPrint(((PairExpList)el).tail,it.t);
        }
        else{
           IntAndTable it = interpExp(((LastExpList)el).head,t);
            System.out.println(it.i);
            return it.t; 
        }
    }
    
}
