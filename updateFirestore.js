const admin = require('firebase-admin');

// Asegúrate de tener el archivo de credenciales del servicio JSON
const serviceAccount = require('./gympet-ea31e-firebase-adminsdk-vyut5-4b7795c900.json');

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount)
});

const db = admin.firestore();

async function updateFirestoreDocuments() {
  try {
    const snapshot = await db.collection('exercises').get();
    snapshot.forEach(async (doc) => {
      const data = doc.data();

      // Convert reps to array of integers if it is not already an array of integers
      if (data.reps) {
        let repsIntArray = [];
        if (!Array.isArray(data.reps)) {
          repsIntArray = [parseInt(data.reps)];
        } else {
          repsIntArray = data.reps.map((rep) => parseInt(rep));
        }
        await doc.ref.update({ reps: repsIntArray });
        console.log(`Updated reps for document ${doc.id}`);
      }

      // Convert sets to array of integers if it is not already an array of integers
      if (data.sets) {
        let setsIntArray = [];
        if (!Array.isArray(data.sets)) {
          setsIntArray = [parseInt(data.sets)];
        } else {
          setsIntArray = data.sets.map((set) => parseInt(set));
        }
        await doc.ref.update({ sets: setsIntArray });
        console.log(`Updated sets for document ${doc.id}`);
      }
    });
    console.log('Firestore documents updated successfully');
  } catch (error) {
    console.error('Error updating documents: ', error);
  }
}

updateFirestoreDocuments();






