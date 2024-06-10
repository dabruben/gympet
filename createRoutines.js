const admin = require('firebase-admin');

// Asegúrate de tener el archivo de credenciales del servicio JSON
const serviceAccount = require('./gympet-ea31e-firebase-adminsdk-vyut5-4b7795c900.json');

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount)
});

const db = admin.firestore();

const routines = [
  {
    title: "Back Workout",
    exerciseCount: 4,
    exercises: [
      {
        name: "Pull-ups",
        description: "An upper body exercise that targets the back and biceps.",
        sets: [4],
        reps: [10]
      },
      {
        name: "Bent Over Rows",
        description: "Targets the middle back and biceps.",
        sets: [4],
        reps: [12]
      },
      {
        name: "Lat Pulldowns",
        description: "Focuses on the latissimus dorsi muscles.",
        sets: [4],
        reps: [12]
      },
      {
        name: "Deadlifts",
        description: "A full-body exercise that emphasizes the lower back and legs.",
        sets: [4],
        reps: [10]
      }
    ]
  },
  {
    title: "Chest Workout",
    exerciseCount: 4,
    exercises: [
      {
        name: "Bench Press",
        description: "A compound exercise that targets the chest, shoulders, and triceps.",
        sets: [4],
        reps: [12]
      },
      {
        name: "Incline Dumbbell Press",
        description: "Focuses on the upper portion of the chest and shoulders.",
        sets: [4],
        reps: [12]
      },
      {
        name: "Chest Fly",
        description: "An isolation exercise that targets the chest muscles.",
        sets: [4],
        reps: [12]
      },
      {
        name: "Cable Crossovers",
        description: "Isolates the chest muscles for a full range of motion.",
        sets: [4],
        reps: [15]
      }
    ]
  },
  {
    title: "Leg Workout",
    exerciseCount: 4,
    exercises: [
      {
        name: "Squats",
        description: "A compound exercise that targets the quadriceps, hamstrings, and glutes.",
        sets: [4],
        reps: [15]
      },
      {
        name: "Leg Press",
        description: "Focuses on the quadriceps, hamstrings, and glutes.",
        sets: [4],
        reps: [12]
      },
      {
        name: "Lunges",
        description: "Targets the quadriceps, hamstrings, and glutes.",
        sets: [4],
        reps: [12]
      },
      {
        name: "Calf Raises",
        description: "Targets the calf muscles.",
        sets: [4],
        reps: [15]
      }
    ]
  }
];

async function createRoutines() {
  try {
    for (const routine of routines) {
      await db.collection('routines').add(routine);
      console.log(`Added routine: ${routine.title}`);
    }
    console.log('All routines added successfully');
  } catch (error) {
    console.error('Error adding routines: ', error);
  }
}

createRoutines();
