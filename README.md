Treej
=====

Feed-forward Event Tree Framework for Java.

Overview
========

Event trees are often used to generate system events that are part of event tree sequences. When an event gets generated on a given node, the framework
will propagate and notify all of the nodes attached to this source node recursively. EventNodes are tuples carry an ID and a value (payload) which can be a reference 
to any given class that you with to contain. All EventNodes implement the "Observable" interface, which means you can register an observer into it. 

Note: Since nodes are notified recursively, the depth of the tree will be limited by the size of your JVM's stack.

  	
This framework provides the following:
  
  1. Extensible event tree framework 

  2. Simple traceability of events (tree path)
  

Example
========

In the following tree:

                 ( "root" )
                   /
				 /
		  ( "Node1" )
  	         /   \
  	       /	   \ 
     ( "Node2" )  ( "Node3" )
		  
An event on the "root" node will cause the following trace: "root" -> "Node1" -> "Node2" & "Node3"