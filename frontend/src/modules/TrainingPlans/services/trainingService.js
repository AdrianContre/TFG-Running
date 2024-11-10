const END_POINT_TRAINING_PLANS = "http://localhost:8081/api/v1/trainingplans"

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

export const getOtherUsersPlans = async () => {
    const authHeader = `Bearer ${localStorage.getItem('token')}`
    return fetch(`${END_POINT_TRAINING_PLANS}`,{
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

export const getMyPlans = async (trainerId) => {
  const authHeader = `Bearer ${localStorage.getItem('token')}`
  return fetch(`${END_POINT_TRAINING_PLANS}/trainers/${trainerId}`,{
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

export const getPlanInfo = async (planId) => {
  const authHeader = `Bearer ${localStorage.getItem('token')}`
  return fetch(`${END_POINT_TRAINING_PLANS}/${planId}`,{
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

export const getUserEnrolledPlans = async (userId) => {
  const authHeader = `Bearer ${localStorage.getItem('token')}`
  return fetch(`${END_POINT_TRAINING_PLANS}/enrolled/${userId}`,{
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

export const enrollUserToPlan = async (planId, userId) => {
  const authHeader = `Bearer ${localStorage.getItem('token')}`
  return fetch(`${END_POINT_TRAINING_PLANS}/${planId}/enroll`,{
      method: 'POST',
      headers: {
      'Authorization': authHeader,
      'Content-Type': 'application/json',
      },
      body: JSON.stringify({userId: userId})
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

export const unenrollUserToPlan = async (planId, userId) => {
  const authHeader = `Bearer ${localStorage.getItem('token')}`
  return fetch(`${END_POINT_TRAINING_PLANS}/withdraw/${planId}`,{
      method: 'DELETE',
      headers: {
      'Authorization': authHeader,
      'Content-Type': 'application/json',
      },
      body: JSON.stringify({userId: userId})
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