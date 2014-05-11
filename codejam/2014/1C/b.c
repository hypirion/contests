#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int M = 1000000007;

int perms(int i, char d[200], char last, char **cc) {
  if (i == 0) {
    return 1;
  }
  int shift = 0;
  int count = 0;
  while (i >> shift != 0) {
    if (((i >> shift) & 1) == 1) {
      char *c = cc[shift];
      char dcpy[200];
      memcpy(dcpy, d, sizeof(dcpy));
      char usable = 1;
      char nlast = last;
      if (dcpy[nlast] == 0 || nlast == c[0]) {
        for (int j = 0; c[j] != 0; j++) {
          char cur = c[j];
          if (cur != nlast && dcpy[nlast] != 0) {
            usable = 0;
          }
          dcpy[cur]--;
          nlast = cur;
        }
      }
      else {
        usable = 0;
      }
      if (usable) {
        count += perms(i & (~(1 << shift)), dcpy, nlast, cc);
        count %= M;
      }
    }
    shift++;
  }
  return count;
}

int main(void) {
  int T;
  scanf("%d", &T);
  for (int t = 1; t <= T; t++) {
    int N;
    scanf("%d", &N);
    char** cc = malloc(sizeof(char*) * N);
    for (int i = 0; i < N; i++) {
      char* c = malloc(sizeof(char) * 200);
      scanf("%s", c);
      cc[i] = c;
    }
    char d[200] = {0};
    for (int i = 0; i < N; i++) {
      char* c = cc[i];
      for (int j = 0; c[j] != 0; j++) {
        d[c[j]]++;
      }
    }
    printf("Case #%d: %d\n", t, perms((1 << N) - 1, d, 0, cc));
    for (int i = 0; i < N; i++) {
      free(cc[i]);
    }
    free(cc);
  }
  return 0;
}
