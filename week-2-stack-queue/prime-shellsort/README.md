# Introduction

It's two Shell Sort algorithms with increments: 

- prime number
- 3x + 1

Let's test if prime number is a good increment.

# Create test file
```
$ touch int30000.txt
$ java-algs4 PrimeShellSort 30000 > int30000.txt
```


# Run
```
$ java-algs4 PrimeShellSort < int30000.txt
```

# Debug
If you do not install the `java-algs4`, you can run
```
$ cd week-2-stack-queue/prime-shellsort
$ cp ../../algs4.jar algs4.jar
$ javac -cp .:algs4.jar -Xlint:unchecked *.java
$ #create
$ java -cp .:algs4.jar PrimeShellSort 30000 > int30000.txt
$ #run
$ java -cp .:algs4.jar PrimeShellSort < int30000.txt
```

# Result

Prime number is not a good increment.
```
$ java-algs4 PrimeShellSort < int30000.txt 
size: 30000
3x + 1 in 0.014s
prime in 0.109s
$ java-algs4 PrimeShellSort < int30000.txt 
size: 30000
3x + 1 in 0.015s
prime in 0.101s
$ java-algs4 PrimeShellSort < int30000.txt 
size: 30000
3x + 1 in 0.014s
prime in 0.108s
$ java-algs4 PrimeShellSort < int30000.txt 
size: 30000
3x + 1 in 0.015s
prime in 0.104s
```

