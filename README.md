## 1 How to run ##

Main class: animator.EasyAnimator  
   Mandatory arguments:  
>    -in       input file  
>    -view     specified view (text, svg, visual, playback)  

Other arguments:  
>    -speed    ticks per second(integer)  
>    -out      output file  


## 2 Main ideas ##
Model package uses Attribute as the interface for shape properties which can change over time (e.g., color, size, etc.)  
It uses AnimateTime as another interface for time.  
One Element<AnimateTime, Attribute> indicates one state in the animation time line.  
An interval [*element_0*, *element_1*] has two above elements, representing an animation over one attribute,(changes from Attribute0 to Attribute1 from time t0 to time t1).  

An AnimateShape consists of several lists of intervals, indicating the animations over its different attributes.  

This project also defines one generic data structure model.IntervalList to handle a list of sorted non-overlapping intervals. It allows borders overlapping with equal attributes. Its main function is handling inserting intervals (animations).  
Every shape contains several IntervalLists.  

Another helper function mergeMulti() is in model.Utils package. It can merge several lists of Intervals and add name attributes. After merging, we are still able to track one attribute's  corresponding animation shape.  

ShapeOut interface is the output generated from AnimateShape, it only contains function for displaying animation shape without modifying it.  

AnimateOutput interface is the output generated from AnimateModel. It is designed to contain functions of playing animation but without the function of inserting shapes and motions. By calling next(), it would go to next tick (if not paused or finished).  
For text view and svg view, AnimateOutput contains all information they need to produce an overall text/svg representation.  
For GUI view and playback view, AnimateOutput contains necessary functions (switch from start to pause or vice versa, switch from looping to not looping or vice versa, reset, etc.) and can generate an AnimateFrame in each tick for them to display.  

## 3. About MVC ##
**Model**   
See above.

**View**  
View interface contains main functions including: updating view's source object, updating view's playing speed and the display function.  
There are four types of views. TextView would use AnimateOutput's toString() and toString(tick) function to display strings.   
SvgView is able to tranform AnimateOutput to SVG format.  
GuiView and PlaybackView need Controller to update the animate frame in each tick. For PlaybackView, controller would control the playing state (start, pause, resume, restart, looping) by invoking AnimateOutput's corresponding functions. But playing speed is controlled by command line or the user input directly to view. The reason is that playing speed can be seen as a basic attribute of GuiView / PlaybackView, indicating the frequency for updating.  

**Controller**  
The constructor of controller only takes one view argument. By invoking transformAnimation(AnimateModel, tick), the controller is able to make the view display animation. So, this function needs one AnimateModel provided from other utilities.
Controller is also an action listener for GuiView and PlaybackView. For GuiView, it simply updates animate frame in each event. For PlayBackview, event source can also be UI components and controller can invoke AnimateOutput's methods.
A mouse adapter delegation is also added to controller.

## 4. Changes made in progress ##
To support TextView and SvgView, I added ShapeOutput and AnimateOutput to model.  
To support GuiView , I added generate animate frame function to AnimateOutput.  
To support PlaybackView, I added control(next, start, pause, resume, restart and looping) functions to AnimateOutput.

## 5 Testing ##
I tested AnimateOutput to complete the whole tests. 
For controller and listener, I tested them with fake view and configured events.

## 6. Files classifications ##
**Model:**
> 	enum:	Shapes
> 	
> 	attributes:
> 		interface: Position, Size, Color, Length
> 		implementation: SizeRectangle, SizeOval, Position2d, ColorRgb, LengthDouble
>                 
> 	main model (inserting shape and motions):
> 		interface: AnimateShape, AnimateTime
> 		implementation: AnimateRectangle, AnimateOval, AnimateTimeInt
> 	
> 	output model(animation data and playing animation function):
> 		interface: AnimateOutput, ShapeOutput
> 		implementation: AnimateOutputImpl, ShapeOutputImpl
> 	
> 	plain data objects(only getters): AnimateFrame
> 
> 	generic data structure:
> 		interface: IntervalList, Element
> 		implementation: IntervalListImpl, IntervalListTreeMapImpl, SimpleElement
> 
> 	plain data objects(only getters): Interval
>                 
>     Utils: Utils.java

**View:**
> 	interface: View, TextView, SvgView, GuiView, PlaybackView
> 	implementation: TextViewImpl, SvgViewImpl, GuiViewImpl, PlaybackViewImpl

**Controller:**
> 	interface: Controller
> 	implementation: ControllerImpl
> 	
> 	additional: MousePosListener

**Main:**
> 	EasyAnimator
> 	Enums: ViewOptions

