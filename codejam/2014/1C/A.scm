#!/usr/bin/guile -s
!#

(use-modules (ice-9 popen))

(define max-height 40)

(define (solve part)
  (let ((elvish (* part (expt 2 max-height))))
    (if (not (integer? elvish))
        "impossible"
        (let loop ((h 1))
          (if (<= 1 (* part (expt 2 h))) h
              (loop (+ h 1)))))))

(define T (read))

(let main ((t 1))
  (when (<= t T)
        (format #t "Case #~A: ~A~%" t (solve (read)))
        (main (+ t 1))))
