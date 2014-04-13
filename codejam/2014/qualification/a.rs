// rust 0.10
extern crate collections;

use std::io;
use std::io::IoResult;

use collections::hashmap::HashSet;

fn main () {
    
    let stdin = &mut PushbackReader::new(~io::stdin());

    let cases = readInt(stdin);
    for t in range(1, cases + 1) {
        let first = readRow(stdin);
        let second = readRow(stdin);

        let mut correct: int = -1;
        let mut iters: int = 0;
        for element in first.intersection(&second) {
            correct = *element;
            iters += 1;
        }
        match iters {
            0 => { println!("Case \\#{}: Volunteer cheated!", t) }
            1 => { println!("Case \\#{}: {}", t, correct) }
            _ => { println!("Case \\#{}: Bad magician!", t) }
        }
    }
}

fn readRow(stdin: &mut PushbackReader) -> HashSet<int> {
    let toRead = readInt(stdin);
    let mut hs = HashSet::new();
    for row in range(1, 5) {
        for _ in range(0, 4) {
            let colVal = readInt(stdin);
            if row == toRead {
                hs.insert(colVal);
            }
        }
    }
    return hs;
}

fn readInt(stdin: &mut PushbackReader) -> int {
    removeWs(stdin);
    let mut res: int = 0;
    // Perhaps consider reading in negative values here later
    loop {
        let c = stdin.read_byte().unwrap();
        if c >= '0' as u8 && c <= '9' as u8 {
            res = res * 10 + (c as int) - ('0' as int);
        }
        else {
            stdin.unread(c);
            break;
        }
    }
    return res;
}

// Removes all whitespacey u8-values
fn removeWs(stdin: &mut PushbackReader) -> () {
    let b: u8 = stdin.read_byte().unwrap();
    if b == ' ' as u8 || b == '\t' as u8 || b == '\n' as u8 || b == '\r' as u8 {
        removeWs(stdin)
    }
    else {
        stdin.unread(b)
    }
}

struct PushbackReader {
    rd: ~Reader,
    buffer: ~[u8],
    pos: uint
}

impl PushbackReader {
	  fn new(rd: ~Reader) -> PushbackReader {
		    PushbackReader {
			      rd: rd,
			      buffer: ~[],
			      pos: 0
		    }
	  }
    
	  fn unread(&mut self, b: u8) {
		    if self.pos == 0 {
			      // Reallocate
			      let sz = if self.buffer.len() == 0 {32} else {2*self.buffer.len()};
			      let mut new_vec = std::slice::from_fn(sz, |_| 0u8);
			      unsafe {
				        new_vec.mut_slice_from(sz - self.buffer.len()).copy_memory(self.buffer);
			      }
			      self.pos = sz - self.buffer.len();
			      self.buffer = new_vec;
		    }
		    self.pos -= 1;
		    self.buffer[self.pos] = b;
	  }
}

impl Reader for PushbackReader {
	  fn read(&mut self, buf: &mut [u8]) -> IoResult<uint> {
		    if self.pos == self.buffer.len() {
			      return self.rd.read(buf);
		    }
		    let sz = std::cmp::min(buf.len(), self.buffer.len() - self.pos);
		    unsafe {
			      buf.mut_slice_to(sz).copy_memory(self.buffer.slice(self.pos, self.pos + sz));
		    }
		    self.pos += sz;
		    Ok(sz)
	  }
}
