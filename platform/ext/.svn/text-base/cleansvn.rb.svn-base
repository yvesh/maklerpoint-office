#!/usr/bin/env ruby
# note above line may need to be changed on linux - use 'which ruby' to find path to ruby
# by Ivan Storck 13 February 2008
puts "The current directory is #{ENV["PWD"]}"
puts "About to delete ALL subversion info from this directory and all directories below"
print "WARNING - this is permanent! Type YES to continue:"
confirm = gets
if confirm == "YES\n" or confirm == nil
  puts `find . -name .svn -type d -print0 | xargs -0 rm -rf`
  puts "Deleted all subversion info"
else
  puts "Command aborted"
end
