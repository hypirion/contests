import Text.Printf
import Control.Applicative
import Control.Monad

main = readLn >>= \n -> forM_ [1::Integer .. n] $ \i -> do
  ws <- words <$> getLine
  printf "Case #%d: %.7f\n" i (best (map read ws :: [Double]))

pairs xs = zip xs (tail xs)

solns fp fr goal =
  map (\(t, r) -> t + goal / r) $
  iterate (\(t, r) -> (t + (fp / r), r + fr)) (0.0, 2.0)

best (fp:fr:goal:[]) =
  fst $ head $ dropWhile (\(a, b) -> a > b) $ pairs $ solns fp fr goal
