# The Javelin Programming Language

(C) 2015 KIM Taegyoon

Javelin is a dialect of Lisp. It is designed to be an embedded language (minimal Lisp for the Java Virtual Machine).

## Compile ##
Run `compile.bat`.

## Run ##
Run `javelin.bat` or,
```
Usage:
java javelin.Core [OPTIONS...] [FILES...]
java -cp javelin-version.jar javelin.Core [OPTIONS...] [FILES...]
java -jar javelin-version.jar [OPTIONS...] [FILES...]

OPTIONS:
    -h    print this screen.
    -v    print version.
```

Run `DrJavelin.bat` to run a simple GUI REPL.

## Reference ##
```
Predefined Symbols:
 * + - . .get .set! / < <= = == > >= and apply def defmacro do doseq eval false filter fn fold if import let list loop map mod new nil nil? not not= or pr prn proxy quote read-line read-string recur set! slurp spit str symbol thread true type
Macros:
 defn when while
```

## Examples ##
### Hello, World! ###
```
(prn "Hello, World!")
```

```
(. javax.swing.JOptionPane showMessageDialog nil "Hello, World!") ; GUI Hello, World!
```

### Comment ###
```
; end-of-line comment
```

### Data types ###
You can use all Java's data types.

Literals:
```
> 3
3 : java.lang.Integer
> 3L
3 : java.lang.Long
> 3.0
3.0 : java.lang.Double
> 3e3
3000.0 : java.lang.Double
> "string"
"string" : java.lang.String
```

### Special form ###
```
> (let (a 1 b 2) (+ a b))
3 : java.lang.Integer
> (doseq (x '(1 2 3)) (pr x))
123nil : nil
```

### Function ###

In a function, [lexical scoping](http://en.wikipedia.org/wiki/Lexical_scoping#Lexical_scoping) is used.

```
> ((fn (x y) (+ x y)) 1 2)
3 : java.lang.Integer
> ((fn (x) (* x 2)) 3)
6 : java.lang.Integer
> (defn foo (x & more) (list x more)) ; variadic function
#<function:[[x, &, more], [list, x, more]]> : javelin.Core$fn
> (foo 1 2 3 4 5)
[1, [2, 3, 4, 5]] : java.util.ArrayList
> (defn sum (x y) (+ x y))
#<function:[[x, y], [+, x, y]]> : javelin.Core$fn
> (sum 1 2)
3 : java.lang.Integer
> (fold + '(1 2 3))
6 : java.lang.Integer
> (defn even? (x) (== 0 (mod x 2)))
#<function:[[x], [==, 0, [mod, x, 2]]]> : javelin.Core$fn
> (even? 3)
false : java.lang.Boolean
> (even? 4)
true : java.lang.Boolean
> (apply + (list 1 2 3))
6 : java.lang.Integer
> (map (fn (x) (. Math sqrt x)) (list 1 2 3 4))
[1.0, 1.4142135623730951, 1.7320508075688772, 2.0] : java.util.ArrayList
> (filter even? (list 1 2 3 4 5))
[2, 4] : java.util.ArrayList
> (= "abc" "abc") ; Object.equals()
true : java.lang.Boolean
> (def x 1)
  ((fn (x) (prn x) (set! x 3) (prn x)) 4) ; lexical scoping
  x
4
3
1 : java.lang.Integer
> (defn adder (amount) (fn (x) (+ x amount))) ; lexical scoping
  (def add3 (adder 3))
  (add3 4)
7 : java.lang.Integer
> (symbol "a")
a : javelin.Core$symbol
```

#### Recur
Evaluates the arguments in order. Execution then jumps back to the recursion point, a loop or fn method.

Warning: `recur` does not check the tail position.
```
> (defn sum1 (n s) (if (< n 1) s (recur (- n 1) (+ s n))))
> (defn sum (n) (sum1 n 0))
> (defn sum-nonrecur (n) (if (< n 1) 0 (+ n (sum-nonrecur (- n 1)))))
> (sum 100)
5050 : java.lang.Integer
> (sum-nonrecur 100)
5050 : java.lang.Integer
> (sum 1000)
500500 : java.lang.Integer
> (sum-nonrecur 1000) ; stack overflow
Exception in thread "main" java.lang.StackOverflowError
> (loop (i 0) (when (< i 5) (pr i) (recur (+ i 1))))
01234nil : nil
```

### Scope ###
`doseq`, `fn`, `let`, `thread` make new scope.

### List ###
```
> (. (list 2 4 6) get 1)
4 : java.lang.Integer
> ((list 2 4 6) 1) ; implicit indexing
4 : java.lang.Integer
> (. (list 1 2 3) size)
3 : java.lang.Integer
```

### Macro ###
```
> (defmacro infix (a op ...) (op a ...)) (infix 3 + 4 5)
12
```

### Thread ###
```
> (def t1 (thread (loop (i 1) (when (<= i 10) (pr "" i) (recur (+ i 1)))))) (def t2 (thread (loop (i 11) (when (<= i 20) (pr "" i) (recur (+ i 1)))))) (. t1 join) (. t2 join)
  111 12 2 13 3 14 4 15 5 16 6 17 7 18 8 19 9 20 10nil : nil
```

### Java interoperability (from Javelin) ###
```
> (import) ; shows current import list. java.lang is imported by default. Classes are found in this order.
[java.lang] : java.util.ArrayList
> (import java.util)
[java.lang, java.util] : java.util.ArrayList
> (new Date)
Tue Sep 22 14:33:28 KST 2015 : java.util.Date
> (. Math random) ; class's static method.
0.4780254852371699 : java.lang.Double
> (. Math floor 1.5)
1.0 : java.lang.Double
> (. "abc" length) ; object's method
3 : java.lang.Integer
> (. true toString)
true : java.lang.String
> (def i 3)
3 : java.lang.Integer
> (. i doubleValue)
3.0 : java.lang.Double
> (.get Math PI) ; get field
3.141592653589793 : java.lang.Double
> (.get javelin.Core testField)(.get Core testField)
nil : nil
> (.set! javelin.Core testField 1) ; set field
  (.get javelin.Core testField)
1 : java.lang.Integer
> (.set! javelin.Core testField "abc")
  (.get javelin.Core testField)
abc : java.lang.String
> (. (new java.math.BigInteger "2") pow 100) ; 2 ^ 100
1267650600228229401496703205376 : java.math.BigInteger
```

#### Proxy example
```
(import javax.swing java.awt java.awt.event)

(def frame (new JFrame))
(def button (new Button "Hello"))
(. button addActionListener
	(proxy java.awt.event.ActionListener
	  (actionPerformed (e)
		(. javax.swing.JOptionPane showMessageDialog nil (str "Hello, World! " e)))))
(. frame setDefaultCloseOperation (.get JFrame EXIT_ON_CLOSE))
(. frame add button (.get BorderLayout NORTH))
(. frame pack)
(. frame setVisible true)
```

#### KOSPI200 Ticker
```
; KOSPI200 Ticker (C) 2015 KIM Taegyoon
(import java.net java.io java.util)

(defn read-url (address)
  (def url (new URL address))
  (def stream (. url openStream))
  (def buf (new BufferedReader (new InputStreamReader stream)))
  (def r "")
  (while (not (nil? (def s (. buf readLine))))
      (set! r (str r s "\n")))
  (. buf close)
  r)

(defn get-quote ()
  (def text (read-url "http://kosdb.koscom.co.kr/main/jisuticker.html"))
  (def p (. regex.Pattern compile "KOSPI200.*</font>"))
  (def m (. p matcher text))
  (if (. m find) (. m group) ""))

(loop ()
  (prn (new Date))
  (prn (get-quote))
  (. Thread sleep 2000)
  (recur))
```

### Java interoperability (from Java) ###
Player.java
```
public class Player {
    private int life;
    public int getLife() {
        return life;
    }
    public void setLife(int life) {
        this.life = life;
    }
}
```

InteropTest.java
```
public class InteropTest {
    public static Object testField;
    public static void main(String[] args) {
        javelin.Core j = new javelin.Core();
        Player pl = new Player();

        // Method 1: using class's field
        testField = pl;
        j.eval_string("(def pl (.get InteropTest testField))");
        j.eval_string("(. pl setLife 100)");
        System.out.println(j.eval_string("(. pl getLife)").intValue());

        // Method 2: not using class's field, set variable to Java's local variable
		j.set("p12", pl); // set
		Player pl2 = j.get("pl2"); // get
        j.eval_string("(. pl2 setLife 200)");
        System.out.println(j.eval_string("(. pl2 getLife)").intValue());
    }
}

```

See the source code for details.

## License ##

   Copyright 2015 KIM Taegyoon

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

   [http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
