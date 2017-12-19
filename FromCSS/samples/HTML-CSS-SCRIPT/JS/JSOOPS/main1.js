function Animal(name) {
  this.name = name
}

Animal.prototype = { 
  canWalk: true,
}

Animal.prototype.sit = function() {
	this.canWalk = false
    console.log(this.name + ' sits down.')
}

var animal = new Animal('Pet') // (1)
console.log(animal.canWalk) // true
animal.sit()             // (2)
console.log(animal.canWalk) // false

function Rabbit(name) {
	this.name = name;
}

Rabbit.prototype = extend(Rabbit, Animal) //Inherits from parent

Rabbit.prototype = {
	canWalk: true,
	jump: function() {
		console.log(this.name + " is Jumps")
	}
}

var rabbit = new Rabbit("Rabbit");

rabbit.jump();
rabbit.sit();

function extend(Child, Parent) {
	console.log("extending..");
	Child.prototype = inherit(Parent.prototype)
	Child.prototype.constructor = Child
	Child.parent = Parent.prototype
}

function inherit(proto) {
	console.log("Inheriting..");
	function F() {}
	F.prototype = proto
	return new F
}
