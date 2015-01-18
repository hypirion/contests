// rust 1.0 alpha
#![allow(unstable)]

use std::{io,fmt};

use Direction::{North, East, South, West};
use Tile::{Walkable, Blocked};
type Point = (usize, usize);

fn main() {
    let mut stdin = io::stdin();

    let l = stdin.read_line().unwrap();
    let trimmed = l.as_slice().trim();
    let t : i32 = trimmed.parse().unwrap();
    for i in range(1, t+1) {
        let l = stdin.read_line().unwrap();
        let trimmed = l.as_slice().trim();
        let pair : Vec<i32> = trimmed.split_str(" ").map(|x| x.parse::<i32>().unwrap()).collect();
        let (height, _) = (pair[0], pair[1]);
        let mut init_board : Vec<Vec<InitTile>> = Vec::new();
        let mut start : Point = (0,0);
        let mut goal : Point = (0,0);
        for row in range(0, height) {
            init_board.push(init_maze_row(&row, &mut start, &mut goal));
        }
        let mazes = make_mazes(&mut init_board);
        let res = match solve(start, goal, &mazes) {
            None => "impossible".to_string(),
            Some(i) => format!("{}", i),
        };

        println!("Case #{}: {}", i, res);
    }
}

fn solve(start : Point, goal : Point, mazes : &[Vec<Vec<Tile>>; 4]) -> Option<i32> {
    let mut been_there : [Vec<Vec<bool>>; 4] =
        [Vec::new(), Vec::new(), Vec::new(), Vec::new()];
    for m in been_there.iter_mut() {
        for i in range(0, mazes[0].len()) {
            m.push(Vec::new());
            for _ in range(0, mazes[0][i].len()) {
                m[i].push(false);
            }
        }
    }
    let mut steps = 0;
    let mut mnum : usize = 1;

    been_there[0][start.0][start.1] = true;
    let mut old_queue = vec!(start);

    while !old_queue.is_empty() {
        let mut new_queue = Vec::new();
        for pos in old_queue.iter() {
            if *pos == goal {
                return Some(steps);
            }
            add_position(*pos, &mut new_queue, &mazes[mnum], &mut been_there[mnum]);
        }
        steps += 1;
        mnum = (mnum + 1) % 4;
        old_queue = new_queue;
    }
    None
}

fn add_position(pos : Point, queue : &mut Vec<Point>, maze : &Vec<Vec<Tile>>, been_there : &mut Vec<Vec<bool>>) {
    // handle edges
    let mut potential = Vec::new();
    if pos.0 != 0 {
        potential.push((pos.0 - 1, pos.1));
    }
    if pos.0 + 1 != maze.len() {
        potential.push((pos.0 + 1, pos.1));
    }
    if pos.1 != 0 {
        potential.push((pos.0, pos.1 - 1));
    }
    if pos.1 + 1 != maze[0].len() {
        potential.push((pos.0, pos.1 + 1));
    }
    for &(y, x) in potential.iter() {
        if maze[y][x] == Walkable && !been_there[y][x] {
            been_there[y][x] = true;
            queue.push((y,x));
        }
    }
}


fn print_board<T: fmt::Show>(board : &Vec<Vec<T>>) {
    for row in board.iter() {
        println!("{:?}", row);
    }
}

fn make_mazes(init : &mut Vec<Vec<InitTile>>) -> [Vec<Vec<Tile>>; 4] {
    let mut mazes = [Vec::new(), Vec::new(), Vec::new(), Vec::new()];
    for m in mazes.iter_mut() {
        for i in range(0, init.len()) {
            m.push(Vec::new());
            for j in range(0, init[i].len()) {
                m[i].push(match init[i][j] {
                    InitTile::Walkable => Walkable,
                    InitTile::Blocked => Blocked,
                    InitTile::Laser(_) => Blocked,
                });
                init[i][j] = rotate(init[i][j]);
            }
        }
    }
    for m in mazes.iter_mut() {
        for i in range(0, init.len()) {
            for j in range(0, init[i].len()) {
                // locate lasers (and rotate them at the end)
                match init[i][j] {
                    InitTile::Laser(dir) => { beamify(dir, i, j, m, init) },
                    _ => { },
                };

                init[i][j] = rotate(init[i][j]);
            }
        }
    }
    mazes
}

fn beamify(d : Direction, i_orig : usize, j_orig : usize, m : &mut Vec<Vec<Tile>>,
           orig : &Vec<Vec<InitTile>>) {
    match d {
        North => {
            for i in std::iter::range_step_inclusive(i_orig as i32 - 1, 0, -1) {
                if orig[i as usize][j_orig] != InitTile::Walkable {
                    break; // Laser can't go through
                }
                m[i as usize][j_orig] = Blocked;
            }
        },
        East => {
            for j in std::iter::range(j_orig + 1, m[0].len()) {
                if orig[i_orig][j] != InitTile::Walkable {
                    break; // Laser can't go through
                }
                m[i_orig][j] = Blocked;
            }
        },
        South => {
            for i in std::iter::range(i_orig + 1, m.len()) {
                if orig[i][j_orig] != InitTile::Walkable {
                    break; // Laser can't go through
                }
                m[i][j_orig] = Blocked;
            }
        },
        West => {
            for j in std::iter::range_step_inclusive(j_orig as i32 - 1, 0, -1) {
                if orig[i_orig][j as usize] != InitTile::Walkable {
                    break; // Laser can't go through
                }
                m[i_orig][j as usize] = Blocked;
            }
        },
    }
}

fn init_maze_row(row : &i32, start : &mut Point, goal : &mut Point) -> Vec<InitTile> {
    let l = io::stdin().read_line().unwrap();
    let mut v = Vec::new();
    let mut col = 0;
    for c in l.as_slice().trim().chars() {
        match c {
            '.' => v.push(InitTile::Walkable),
            '#' => v.push(InitTile::Blocked),
            'v' => v.push(InitTile::Laser(South)),
            '<' => v.push(InitTile::Laser(West)),
            '^' => v.push(InitTile::Laser(North)),
            '>' => v.push(InitTile::Laser(East)),
            'S' => {*start = (*row as usize, col); v.push(InitTile::Walkable)},
            'G' => {*goal = (*row as usize, col); v.push(InitTile::Walkable)},
            _ => { }
        }
        col += 1;
    }
    v
}

#[derive(Copy, PartialEq)]
enum Tile {
    Walkable,
    Blocked
}

impl fmt::Show for Tile {
    fn fmt(&self, f: &mut fmt::Formatter) -> fmt::Result {
        match *self {
            Walkable => '.'.fmt(f),
            Blocked => '#'.fmt(f),
        }
    }
}

#[derive(Copy, PartialEq)]
enum InitTile {
    Walkable,
    Blocked,
    Laser(Direction)
}

impl fmt::Show for InitTile {
    fn fmt(&self, f: &mut fmt::Formatter) -> fmt::Result {
        match *self {
            InitTile::Walkable => '.',
            InitTile::Blocked => '#',
            InitTile::Laser(North) => '^',
            InitTile::Laser(West) => '<',
            InitTile::Laser(South) => 'v',
            InitTile::Laser(East) => '>',
        }.fmt(f)
    }
}

#[derive(Copy, PartialEq, Show)]
enum Direction {
    North, East, South, West
}

fn cw(d : Direction) -> Direction {
    match d {
        North => East,
        East => South,
        South => West,
        West => North,
    }
}

fn rotate(it : InitTile) -> InitTile {
    match it {
        InitTile::Laser(d) => InitTile::Laser(cw(d)),
        k => k
    }
}
