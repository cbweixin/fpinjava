package com.fpinjava.functions.monad;

import java.util.function.Function;

enum TxState {
    BEGIN,
    ROLLBACK,
    COMMIT
}

class Database {

    public Database() {
        System.out.println("create a new db");
    }

    void beginTxn() {
        System.out.println("begin transaction");
    }

    void rollback() {
        System.out.println("rollback...");
    }

    void commit() {
        System.out.println("commit...");
    }

}

public class Transactional {
    static Database db = new Database();
    private final TxState txState;

    public Transactional(TxState state) {
        this.txState = state;

    }

    static Transactional begin() {
        db.beginTxn();
        return new Transactional(TxState.BEGIN);
    }

    Transactional map(Function<TxState, TxState> transform) {
        // if current txstate is not begin, then skip
        if(txState != TxState.BEGIN) {
            return this;
        }
        try {
            final TxState result = transform.apply(txState);
            return new Transactional(result);
        } catch (Exception e) {
            // if transaction incidents occured, rollback
            db.rollback();
            return new Transactional(TxState.ROLLBACK);
        }
    }

    Transactional commit() {
        return map(txState -> {
            db.commit();
            return TxState.COMMIT;
        });
    }
}
