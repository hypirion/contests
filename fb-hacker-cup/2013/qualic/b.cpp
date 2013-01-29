#include <iostream>
using namespace std;

int main(void) {
  int T;
  cin >> T;
  string s;
  getline(cin, s);
  for (int t = 1; t <= T; t++) {
    getline(cin, s);
    bool parens[200] = {false};
    parens[0] = true;
    bool p_colon = false;
    for (int c = 0; s[c]; c++){
      switch (s[c]) {
      case ':':
        if (s[c+1] == ')')
          for (int i = 0; i < 199; i++)
            parens[i] |= parens[i+1];
        else if (s[c+1] == '(')
          for (int i = 199; 0 < i; i--)
            parens[i] |= parens[i-1];
        break;
      case ')':
        if (p_colon)
          break;
        for (int i = 0; i < 199; i++)
          parens[i] = parens[i+1];
        break;
      case '(':
        if (p_colon)
          break;
        for (int i = 199; 0 < i; i--)
          parens[i] = parens[i-1];
        parens[0] = false;
        break;
      }
      p_colon = s[c] == ':';
    }

    printf("Case #%d: %s\n", t, parens[0] ? "YES" : "NO");
  }

  return 0;
}
