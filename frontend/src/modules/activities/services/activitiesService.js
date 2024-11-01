const END_POINT_ACTIVITIES = "http://localhost:8081/api/v1/activities"
const END_POINT_MANUAL_ACTIVITIES = "http://localhost:8081/api/v1/manualactivities"

export const listActivities = async (runnerId) => {
    const authHeader = `Bearer ${localStorage.getItem('token')}`
    return fetch(`${END_POINT_ACTIVITIES}/runners/${runnerId}`,{
        method: 'GET',
        headers: {
        'Authorization': authHeader,
        'Content-Type': 'application/json',
        },
    })
    .then(response => {
        // if (!response.ok) {
        //   throw new Error(response);
        // }
        return response.json();
      })
      .then(data => {
        return data;
      })
      .catch(error => {
        console.error('Error:', error);
        throw error; // Lanzar el error para que pueda ser manejado por quien llame a esta función
      });
}

export const createManualActivity = async (name, description, distance, duration, pace, fcAvg, runnerId, materialsId) => {
  const authHeader = `Bearer ${localStorage.getItem('token')}`
    return fetch(`${END_POINT_MANUAL_ACTIVITIES}`,{
        method: 'POST',
        headers: {
        'Authorization': authHeader,
        'Content-Type': 'application/json',
        },
        body: JSON.stringify({name: name, description: description, distance: distance, duration: duration, pace: pace, fcAvg: fcAvg, runnerId: runnerId, materialsId: materialsId}),
    })
    .then(response => {
        // if (!response.ok) {
        //   throw new Error(response);
        // }
        return response.json();
      })
      .then(data => {
        return data;
      })
      .catch(error => {
        console.error('Error:', error);
        throw error; // Lanzar el error para que pueda ser manejado por quien llame a esta función
      });
}

export const addRoute = async (formData, actId) => {
  const authHeader = `Bearer ${localStorage.getItem('token')}`
  return fetch(`${END_POINT_MANUAL_ACTIVITIES}/route/${actId}`,{
      method: 'PUT',
      headers: {
      'Authorization': authHeader,
      
      },
      body: formData,
  })
  .then(response => {
      if (!response.ok) {
        throw new Error(response);
      }
      return response.json();
    })
    .then(data => {
      return data;
    })
    .catch(error => {
      console.error('Error:', error);
      throw error; // Lanzar el error para que pueda ser manejado por quien llame a esta función
    });
}

export const deleteManualActivity = async (manualActId) => {
  const authHeader = `Bearer ${localStorage.getItem('token')}`
  return fetch(`${END_POINT_MANUAL_ACTIVITIES}/${manualActId}`,{
      method: 'DELETE',
      headers: {
      'Authorization': authHeader,
      'Content-Type': 'application/json',
      }, 
  })
  .then(response => {
      if (!response.ok) {
        throw new Error(response);
      }
      return response.json();
    })
    .then(data => {
      return data;
    })
    .catch(error => {
      console.error('Error:', error);
      throw error; // Lanzar el error para que pueda ser manejado por quien llame a esta función
    });
}

export const getManualActivity = async (manualActId) => {
  const authHeader = `Bearer ${localStorage.getItem('token')}`
  return fetch(`${END_POINT_MANUAL_ACTIVITIES}/${manualActId}`,{
      method: 'GET',
      headers: {
      'Authorization': authHeader,
      'Content-Type': 'application/json',
      }, 
  })
  .then(response => {
      if (!response.ok) {
        throw new Error(response);
      }
      return response.json();
    })
    .then(data => {
      return data;
    })
    .catch(error => {
      console.error('Error:', error);
      throw error; // Lanzar el error para que pueda ser manejado por quien llame a esta función
    });
}