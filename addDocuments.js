const admin = require('firebase-admin');

// Asegúrate de tener el archivo de credenciales del servicio JSON
const serviceAccount = require('./gympet-ea31e-firebase-adminsdk-vyut5-4b7795c900.json');

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount)
});

const db = admin.firestore();

const exercises = [
  { name: "Push-ups", instructions: "Start in a plank position, lower your body until your chest nearly touches the floor, then push back up.", sets: 4, reps: 12 },
  { name: "Bench Press", instructions: "Lie on a bench, press the barbell up until your arms are straight, then lower it back to your chest.", sets: 4, reps: 12 },
  { name: "Incline Dumbbell Press", instructions: "Lie on an incline bench, press the dumbbells up until your arms are straight, then lower them back.", sets: 4, reps: 12 },
  { name: "Chest Fly", instructions: "Lie on a bench, hold the dumbbells above your chest, then lower them to the sides in a wide arc.", sets: 4, reps: 12 },
  { name: "Cable Crossovers", instructions: "Stand between two cables, pull the handles together in front of your chest, then return to the start.", sets: 4, reps: 12 },
  { name: "Pull-ups", instructions: "Hang from a bar with an overhand grip, pull your body up until your chin is above the bar, then lower down.", sets: 4, reps: 12 },
  { name: "Bent Over Rows", instructions: "Bend at the waist, hold a barbell with both hands, pull it towards your chest, then lower it back down.", sets: 4, reps: 12 },
  { name: "Lat Pulldowns", instructions: "Sit at a lat pulldown machine, pull the bar down to your chest, then let it rise back up.", sets: 4, reps: 12 },
  { name: "Deadlifts", instructions: "Stand with feet hip-width apart, bend at the hips and knees to lift the barbell, then stand up straight.", sets: 4, reps: 12 },
  { name: "Seated Rows", instructions: "Sit at a rowing machine, pull the handles towards your torso, then extend your arms back out.", sets: 4, reps: 12 },
  { name: "Squats", instructions: "Stand with feet shoulder-width apart, bend your knees and lower your hips as if sitting back in a chair, then stand back up.", sets: 4, reps: 12 },
  { name: "Leg Press", instructions: "Sit at a leg press machine, press the platform away with your feet until your legs are straight, then bend your knees to return.", sets: 4, reps: 12 },
  { name: "Lunges", instructions: "Step forward with one leg, lower your hips until both knees are bent at 90 degrees, then step back.", sets: 4, reps: 12 },
  { name: "Leg Curls", instructions: "Lie face down on a leg curl machine, curl your legs up towards your buttocks, then lower them back down.", sets: 4, reps: 12 },
  { name: "Calf Raises", instructions: "Stand on a raised surface, lift your heels as high as possible, then lower them back down.", sets: 4, reps: 12 },
  { name: "Shoulder Press", instructions: "Sit or stand with a dumbbell in each hand, press the weights overhead until your arms are straight, then lower them back down.", sets: 4, reps: 12 },
  { name: "Lateral Raises", instructions: "Stand with a dumbbell in each hand, lift the weights out to the sides until your arms are parallel to the floor, then lower them back down.", sets: 4, reps: 12 },
  { name: "Front Raises", instructions: "Stand with a dumbbell in each hand, lift the weights in front of you until your arms are parallel to the floor, then lower them back down.", sets: 4, reps: 12 },
  { name: "Rear Delt Fly", instructions: "Bend at the waist with a dumbbell in each hand, lift the weights out to the sides until your arms are parallel to the floor, then lower them back down.", sets: 4, reps: 12 },
  { name: "Arnold Press", instructions: "Sit or stand with a dumbbell in each hand, rotate your palms as you press the weights overhead, then lower them back down.", sets: 4, reps: 12 },
  { name: "Bicep Curls", instructions: "Stand with a dumbbell in each hand, curl the weights up towards your shoulders, then lower them back down.", sets: 4, reps: 12 },
  { name: "Tricep Dips", instructions: "Hold onto parallel bars, lower your body until your arms are bent at 90 degrees, then press back up.", sets: 4, reps: 12 },
  { name: "Hammer Curls", instructions: "Stand with a dumbbell in each hand, curl the weights up towards your shoulders with your palms facing inwards, then lower them back down.", sets: 4, reps: 12 },
  { name: "Skull Crushers", instructions: "Lie on a bench with a barbell, lower the weight towards your forehead by bending your elbows, then press back up.", sets: 4, reps: 12 },
  { name: "Concentration Curls", instructions: "Sit on a bench, rest your elbow on your thigh, curl a dumbbell up towards your shoulder, then lower it back down.", sets: 4, reps: 12 }
];

exercises.forEach(async (exercise) => {
  try {
    await db.collection('exercises').add(exercise);
    console.log(`Added ${exercise.name}`);
  } catch (error) {
    console.error('Error adding document: ', error);
  }
});
