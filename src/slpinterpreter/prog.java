package slpinterpreter;

class prog {
//Example provided by the book
    static stm prog =
            new CompoundStm(new AssignStm("a", 
                                new OpExp(new NumExp(5),
                                            OpExp.Plus, new NumExp(3))),
            new CompoundStm(new AssignStm("b",
            new EseqExp(new PrintStm(new PairExpList(new IdExp("a"),
            new LastExpList(new OpExp(new IdExp("a"), OpExp.Minus,
            new NumExp(1))))),
            new OpExp(new NumExp(10), OpExp.Times, new IdExp("a")))),
            new PrintStm(new LastExpList(new IdExp("b")))));
    //a := 10
    static stm prog2 = 
            new AssignStm("a", new NumExp(10));
    //print(5)
    static stm prog3 =
            new PrintStm(new LastExpList(new NumExp(5)));
    //a := 5 + 3; print(a)
    static stm prog4 = 
            new CompoundStm(new AssignStm("a", new OpExp( new NumExp(5), OpExp.Plus, new NumExp(3))), new PrintStm(new LastExpList(new IdExp("a"))));
   //b := (print (10, 9, 8, (c := (print (11, 10, 9, 8, 7), 6), 5)), 3)
    static stm prog5 =
            new AssignStm("b", new EseqExp(new PrintStm(new PairExpList(new NumExp(10), new PairExpList(new NumExp(9), new PairExpList(new NumExp(8), new LastExpList(new EseqExp(new AssignStm("c", new EseqExp(new PrintStm(new PairExpList(new NumExp(11), new PairExpList(new NumExp(10), new PairExpList(new NumExp(9), new PairExpList(new NumExp(8), new LastExpList(new NumExp(7))))))), new NumExp(6))), new NumExp(5))))))), new NumExp(3)));
    //a:= 1+3; Print((b:=2, 3), 4, 7, (a:= b/2,3));Print(a, b)
    //result should be: 3 4 7 3
    //                  1 2 
    static stm prog6 =
            new CompoundStm(new AssignStm("a", new OpExp( new NumExp(1), OpExp.Plus, new NumExp(3))), 
                new CompoundStm(new PrintStm(new PairExpList(new EseqExp(
                    new AssignStm("b", new NumExp(2)),new NumExp(3)), new PairExpList(new NumExp(4),
                        new PairExpList(new NumExp(7), new LastExpList(new EseqExp(new AssignStm("a", new OpExp(new IdExp("b"), OpExp.Div, new NumExp(2))), new NumExp(3))))))),
                    new PrintStm(new PairExpList(new IdExp("a"), new LastExpList(new IdExp("b"))))));
}
