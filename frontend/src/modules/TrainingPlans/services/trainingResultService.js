const END_POINT_RESULTS = "http://localhost:8081/api/v1/sessionresults"

export const getUserResultsPlan = async (planId, userId) => {
    const authHeader = `Bearer ${localStorage.getItem('token')}`
    return fetch(`${END_POINT_RESULTS}/users/${userId}/plans/${planId}`,{
        method: 'GET',
        headers: {
        'Authorization': authHeader,
        'Content-Type': 'application/json',
        },
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

export const createRunningSessionResult = async (planId, userId, sessionId, description, effort, distance, duration, pace, fcAvg, materials, date) => {
  const authHeader = `Bearer ${localStorage.getItem('token')}`
  return fetch(`${END_POINT_RESULTS}/running`,{
      method: 'POST',
      headers: {
      'Authorization': authHeader,
      'Content-Type': 'application/json',
      },
      body: JSON.stringify({planId: planId, userId: userId, sessionId: sessionId, description: description, effort: effort, distance: distance, duration: duration, pace: pace, fcAvg: fcAvg, materials: materials, date: date})
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

export const createStrengthSessionResult = async (planId, userId, sessionId, description, effort, materials, date) => {
  const authHeader = `Bearer ${localStorage.getItem('token')}`
  return fetch(`${END_POINT_RESULTS}/strength`,{
      method: 'POST',
      headers: {
      'Authorization': authHeader,
      'Content-Type': 'application/json',
      },
      body: JSON.stringify({planId: planId, userId: userId, sessionId: sessionId, description: description, effort: effort, materials: materials, date: date})
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

export const createMobilitySessionResult = async (planId, userId, sessionId, description, effort, materials, date) => {
  const authHeader = `Bearer ${localStorage.getItem('token')}`
  return fetch(`${END_POINT_RESULTS}/mobility`,{
      method: 'POST',
      headers: {
      'Authorization': authHeader,
      'Content-Type': 'application/json',
      },
      body: JSON.stringify({planId: planId, userId: userId, sessionId: sessionId, description: description, effort: effort, materials: materials, date: date})
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