#include<iostream>
#include<map>
#include<set>
#include<queue>
using namespace std;
#define min(a,b) ((a>b?b:a))

int main(void) {
  int T;
  cin >> T;
  for (int t = 1; t <= T; t++){
    uint32_t n, k, a, b, c, r;
    cin >> n >> k >> a >> b >> c >> r;

    queue<uint32_t> q;
    set<uint32_t> s;
    map<uint32_t, uint32_t> hm;
    for (uint32_t i = 0; i < 2*k; i++){
      s.insert(i);
    }

    uint64_t m_i = a;
    for (uint32_t i = 0; i < k; i++) {
      s.erase((uint32_t) m_i);
      hm[m_i] = i;
      q.push((uint32_t) m_i);
      m_i = (b * m_i + c) % r;
    }
    uint32_t m_n = (uint32_t) m_i;
    uint32_t stop = min(3*(k+1), n);
    for (uint32_t i = k; i < stop; i++) {
      m_n = (*s.begin());
      s.erase(m_n);
      uint32_t possibleNext = q.front();
      if (hm[possibleNext] <= i-k)
        s.insert(q.front());
      q.pop();
      q.push(m_n);
    }
    if (stop < n) {
      uint32_t remaining = n - stop;
      remaining %= (k+1);
      if (remaining != 0) {
        m_n = (*s.begin());
        q.push(m_n);
        for (uint32_t i = 1; i < remaining; i++) {
          m_n = q.front();
          q.pop();
          q.push(m_n); //Theoretically not needed.
        }
      }
    }
    cout << "Case #" << t << ": " << m_n << endl;
  }
  return 0;
}
