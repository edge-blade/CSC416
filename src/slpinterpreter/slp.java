package slpinterpreter;


abstract class stm {}
class CompoundStm extends stm {
   stm stm1, stm2;
   CompoundStm(stm s1, stm s2) {stm1=s1; stm2=s2;}
}

class AssignStm extends stm {
   String id; Exp exp;
   AssignStm(String i, Exp e) {id=i; exp=e;}
}

class PrintStm extends stm {
   ExpList exps;
   PrintStm(ExpList e) {exps=e;}
}

abstract class Exp {}

class IdExp extends Exp {
   String id;
   IdExp(String i) {id=i;}
}

class NumExp extends Exp {
   int num;
   NumExp(int n) {num=n;}
}

class OpExp extends Exp {
   Exp left, right; int oper;
   final static int Plus=1,Minus=2,Times=3,Div=4;
   OpExp(Exp l, int o, Exp r) {left=l; oper=o; right=r;}
}

class EseqExp extends Exp {
   stm stm; Exp exp;
   EseqExp(stm s, Exp e) {stm=s; exp=e;}
}

abstract class ExpList {}

class PairExpList extends ExpList {
   Exp head; ExpList tail;
   public PairExpList(Exp h, ExpList t) {head=h; tail=t;}
}

class LastExpList extends ExpList {
   Exp head; 
   public LastExpList(Exp h) {head=h;}
}
class Table{
    String id;
    int value;
    Table tail;
    
    Table(String i, int v, Table t) {
        id=i;
        value=v;
        tail=t;
    }
    
    int lookUp(String key){
        if (id.equals(key)){
            return value;
        }
        else{
            return tail.lookUp(key);
        }
    }
}
class IntAndTable{
    int i; 
    Table t;
   IntAndTable(int ii, Table tt){
       i=ii; 
       t=tt;
       
   }
}