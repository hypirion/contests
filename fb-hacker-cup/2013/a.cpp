#include <iostream>
#include <algorithm>
using namespace std;

int main(void) {
  int N;
  cin >> N;
  string s;
  getline(cin, s);
  for (int i = 1; i <= N; i++){
    getline(cin, s);
    int vs[400] = {0};
    int c = 0;
    while (s[c])
      vs[tolower(s[c++])]++;

    sort(vs+'a',vs+'z'+1);
    int sum = 0;
    for (int i = 'z', m = 26; 0 < m; i--, m--)
      sum += vs[i]*m;

    printf("Case #%d: %d\n", i, sum);
  }

  return 0;
}
