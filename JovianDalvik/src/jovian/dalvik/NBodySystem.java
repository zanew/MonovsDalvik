package jovian.dalvik;

final class NBodySystem {
	   private Body[] bodies;

	   public NBodySystem(){
	      bodies = new Body[]{
	            Body.sun(),
	            Body.jupiter(),
	            Body.saturn(),
	            Body.uranus(),
	            Body.neptune()
	         };

	      double px = 0.0;
	      double py = 0.0;
	      double pz = 0.0;
	      for(int i=0; i < bodies.length; ++i) {
	         px += bodies[i].vx * bodies[i].mass;
	         py += bodies[i].vy * bodies[i].mass;
	         pz += bodies[i].vz * bodies[i].mass;
	      }
	      bodies[0].offsetMomentum(px,py,pz);
	   }

	   public void advance(double dt) {

	      for(int i=0; i < bodies.length; ++i) {
	            Body iBody = bodies[i];
	         for(int j=i+1; j < bodies.length; ++j) {
	                double dx = iBody.x - bodies[j].x;
	            double dy = iBody.y - bodies[j].y;
	            double dz = iBody.z - bodies[j].z;

	                double dSquared = dx * dx + dy * dy + dz * dz;
	                double distance = Math.sqrt(dSquared);
	                double mag = dt / (dSquared * distance);

	            iBody.vx -= dx * bodies[j].mass * mag;
	            iBody.vy -= dy * bodies[j].mass * mag;
	            iBody.vz -= dz * bodies[j].mass * mag;

	            bodies[j].vx += dx * iBody.mass * mag;
	            bodies[j].vy += dy * iBody.mass * mag;
	            bodies[j].vz += dz * iBody.mass * mag;
	         }
	      }

	        for ( Body body : bodies) {
	         body.x += dt * body.vx;
	         body.y += dt * body.vy;
	         body.z += dt * body.vz;
	      }
	   }

	   public double energy(){
	      double dx, dy, dz, distance;
	      double e = 0.0;

	      for (int i=0; i < bodies.length; ++i) {
	            Body iBody = bodies[i];
	            e += 0.5 * iBody.mass *
	                 ( iBody.vx * iBody.vx
	                   + iBody.vy * iBody.vy
	                   + iBody.vz * iBody.vz );

	         for (int j=i+1; j < bodies.length; ++j) {
	                Body jBody = bodies[j];
	                dx = iBody.x - jBody.x;
	            dy = iBody.y - jBody.y;
	            dz = iBody.z - jBody.z;

	            distance = Math.sqrt(dx*dx + dy*dy + dz*dz);
	            e -= (iBody.mass * jBody.mass) / distance;
	         }
	      }
	      return e;
	   }
}
