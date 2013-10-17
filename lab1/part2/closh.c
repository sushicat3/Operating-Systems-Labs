// closh.c - CS 377, Fall 2013
// SWETAL BHATT
// PENG GAO

#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include <signal.h>

#define TRUE 1
#define FALSE 0

// tokenize the command string into arguments - do not modify
void readCmdTokens(char* cmd, char** cmdTokens) {
  cmd[strlen(cmd) - 1] = '\0'; // drop trailing newline
  int i = 0;
  cmdTokens[i] = strtok(cmd, " "); // tokenize on spaces
  while (cmdTokens[i++] && i < sizeof(cmdTokens)) {
    cmdTokens[i] = strtok(NULL, " ");
  }
}

// read one character of input, then discard up to the newline - do not modify
char readChar() {
  char c = getchar();
  while (getchar() != '\n');
  return c;
}

// main method - program entry point
int main() {
  char cmd[81]; // array of chars (a string)
  char* cmdTokens[20]; // array of strings
  int count; // number of times to execute command
  int parallel; // whether to run in parallel or sequentially
  int timeout; // max seconds to run set of commands (parallel) or each command (sequentially)

  while (TRUE) { // main shell input loop

    // begin parsing code - do not modify
    printf("closh> ");
    fgets(cmd, sizeof(cmd), stdin);
    if (cmd[0] == '\n') continue;
    readCmdTokens(cmd, cmdTokens);
    do {
      printf("  count> ");
      count = readChar() - '0';
    } while (count <= 0 || count > 9);
    printf("  [p]arallel or [s]equential> ");
    parallel = (readChar() == 'p') ? TRUE : FALSE;
    do {
      printf("  timeout> ");
      timeout = readChar() - '0';
    } while (timeout < 0 || timeout > 9);
    // end parsing code
    

    ////////////////////////////////////////////////////////
    //                                                    //
    // TODO: use cmdTokens, count, parallel, and timeout  //
    // to implement the rest of closh                     //
    //                                                    //
    // /////////////////////////////////////////////////////

    pid_t pidt;
    pid_t pid[9];
    int i, status;

    // see README for parallel v sequential design choices

    if (parallel) {

      // COUNT-LOOP
      for (i = 0; i<count; i++) {                              // fork N=count processes
        pid[i] = fork();                                       // save the pid and fork
        if (pid[i] == 0) {                                     // CHILD: the process delegated to exec the command
            printf("process %d forked\n", getpid() );       
          //sleep(timeout+1);
          execvp(cmdTokens[0], cmdTokens); 
          printf("Can't execute %s\n", cmdTokens[0]); 
          exit(1);
        }
      }

      if (timeout != 0) {                                      // PARENT:
        sleep(timeout);                                        // sleeps the timeout until
        for (i = 0; i<count; i++) {
          kill(pid[i], SIGKILL);                               // killing the children 
        } 
      }                               
                                                               // (OUTSIDE of count-loop)
      while ( (pidt = waitpid(-1, &status, 0)) > 0 ) {         // PARENT: waits for any terminated children
          printf("process %d terminated\n", pidt);
      }
    }

    else {

      //COUNT-LOOP
      for (i = 0; i<count; i++) {                               // fork N=count processes
        if ( (pidt = fork()) == 0) {                            // save pid and fork, continue as CHILD
            printf("process %d forked\n", getpid() );
          execvp(cmdTokens[0], cmdTokens); 
          printf("Can't execute %s\n", cmdTokens[0]);
          exit(1);
        }
        else {                                                  // PARENT: (INSIDE of count-loop)
          if (timeout != 0) {
            sleep(timeout);                                     // sleeps the timeout until
            kill(pidt, SIGKILL);                                // killing the child
          }
          if ( (pidt = waitpid(-1, &status, 0)) > 0 ) {         // waits for any terminated children
            printf("process %d terminated\n", pidt);
          }
        }
      }
    }

    // just executes the given command once - REPLACE THIS CODE WITH YOUR OWN
    //execvp(cmdTokens[0], cmdTokens); // replaces the current process with the given program
    //printf("Can't execute %s\n", cmdTokens[0]); // only reached if running the program failed
    //exit(1);

  }
}

