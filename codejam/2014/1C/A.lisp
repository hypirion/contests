#!/usr/bin/sbcl --script

(defconstant max-height 40)

(defun solve (part)
  (let ((elvish (* part (expt 2 max-height))))
    (if (not (integerp elvish))
        "impossible"
      (loop named solve
            for h from 1 to max-height
            do (if (<= 1 (* part (expt 2 h)))
                   (return-from solve h))))))

(loop for t- from 1 to (read)
   do (format t "Case #~A: ~A~%" t- (solve (read))))
