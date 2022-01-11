class A {
    f1() {
        log 'hello'
        log A
        return A
    }
}
log freedy.A.f1().f1().f1()