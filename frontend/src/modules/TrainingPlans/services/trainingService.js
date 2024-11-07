const END_POINT_TRAINING_PLANS = "http://localhost:8081/api/v1/trainingplan"

export const createPlan = async (name, description, numWeeks, objDistance, level, sessions, trainerId) => {
    const authHeader = `Bearer ${localStorage.getItem('token')}`
    return fetch(`${END_POINT_TRAINING_PLANS}`,{
        method: 'POST',
        headers: {
        'Authorization': authHeader,
        'Content-Type': 'application/json',
        },
        body: JSON.stringify({name: name, description: description, numWeeks: numWeeks, objDistance: objDistance, level: level, sessions: sessions, trainerId: trainerId})
    })
    .then(response => {
        return response.json();
      })
      .then(data => {
        console.log(data)
        return data.data
      })
      .catch(error => {
        console.error('Error:', error);
        console.log(error)
        throw error; 
      });
}