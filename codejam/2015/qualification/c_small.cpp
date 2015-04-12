// clang++ -std=c++11 -Wall -Wno-char-subscripts -Ofast c_small.cpp

#include<iostream>
#include<map>
#include<set>
#include<queue>
using namespace std;
#define min(a,b) ((a>b?b:a))

#define neg1 '0'
#define negk 'K'
#define negi 'I'
#define negj 'J'

char lut[255][255];
char dp[10001][10001];

bool cnegp(char a) {
  return (a == '0') || (a == 'I') || (a == 'J') || (a == 'K');
}

char cnegate(char a) {
  switch (a) {
    case '0': return '1';
    case '1': return '0';
    case 'i': return 'I';
    case 'I': return 'i';
    case 'j': return 'J';
    case 'J': return 'j';
    case 'K': return 'k';
    case 'k': return 'K';
    }
  return '-';
}

char cabs(char a) {
  if (cnegp(a)) {
    return cnegate(a);
  } else {
    return a;
  }
}

char mul(char a, char b) {
  bool neg = cnegp(a) ^ cnegp(b);
  char absRes = lut[cabs(a)][cabs(b)];
  if (neg) {
    return cnegate(absRes);
  } else {
    return absRes;
  }
}

int main(void) {
  lut['1']['1'] = '1';
  lut['1']['i'] = 'i';
  lut['1']['j'] = 'j';
  lut['1']['k'] = 'k';
  lut['i']['1'] = 'i';
  lut['i']['i'] = '0';
  lut['i']['j'] = 'k';
  lut['i']['k'] = 'J';
  lut['j']['1'] = 'j';
  lut['j']['i'] = 'K';
  lut['j']['j'] = '0';
  lut['j']['k'] = 'i';
  lut['k']['1'] = 'k';
  lut['k']['i'] = 'j';
  lut['k']['j'] = 'I';
  lut['k']['k'] = '0';

  int T;
  cin >> T;
  for (int t = 1; t <= T; t++){
    int l, x;
    cin >> l >> x;
    string theStr;
    cin >> theStr;
    string s = "";
    for (int i = 0; i < x; i++) {
      s += theStr;
    }
    for (int i = 0; i < l * x; i++) {
      char v = s[i];
      for (int j = i+1; j <= l * x; j++) {
        dp[i][j] = v;
        v = mul(v, s[j]);
      }
    }
    for (int i = 1; i < l * x; i++) {
      for (int j = i+1; j < l * x; j++) {
        if (dp[0][i] == 'i' &&
            dp[i][j] == 'j' &&
            dp[j][l * x] == 'k') {
          cout << "Case #" << t << ": YES" << endl;
          goto next_testcase;
        }
      }
    }
    
    cout << "Case #" << t << ": NO" << endl;
  next_testcase:
    ;
  }
  return 0;
}
