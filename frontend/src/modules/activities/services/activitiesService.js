import store from '../../../redux/stores/store.js'
const END_POINT_ACTIVITIES = "http://localhost:8081/api/v1/activities"
const END_POINT_MANUAL_ACTIVITIES = "http://localhost:8081/api/v1/manualactivities"
const END_POINT_RESULTS = "http://localhost:8081/api/v1/sessionresults"


export const listActivities = async (runnerId) => {
    const authHeader = `Bearer ${store.getState().auth.token}`
    return fetch(`${END_POINT_ACTIVITIES}/runners/${runnerId}`,{
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
        return data;
      })
      .catch(error => {
        console.error('Error:', error);
        throw error;
      });
}

export const createManualActivity = async (name, description, distance, duration, pace, fcAvg, runnerId, materialsId, date) => {
  const authHeader = `Bearer ${store.getState().auth.token}`  
  return fetch(`${END_POINT_MANUAL_ACTIVITIES}`,{
        method: 'POST',
        headers: {
        'Authorization': authHeader,
        'Content-Type': 'application/json',
        },
        body: JSON.stringify({name: name, description: description, distance: distance, duration: duration, pace: pace, fcAvg: fcAvg, runnerId: runnerId, materialsId: materialsId,date: date}),
    })
    .then(response => {
        return response.json();
      })
      .then(data => {
        return data;
      })
      .catch(error => {
        console.error('Error:', error);
        throw error;
      });
}

export const addRoute = async (formData, actId) => {
  const authHeader = `Bearer ${store.getState().auth.token}`
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
      throw error;
    });
}

export const deleteManualActivity = async (manualActId) => {
  const authHeader = `Bearer ${store.getState().auth.token}`
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
      throw error;
    });
}

export const getManualActivity = async (manualActId) => {
  const authHeader = `Bearer ${store.getState().auth.token}`
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
      throw error; 
    });
}

export const editManualActivity = async (manualActId, name, description, distance, duration, pace, fcAvg, materialsId, date) => {
  const authHeader = `Bearer ${store.getState().auth.token}`
  return fetch(`${END_POINT_MANUAL_ACTIVITIES}/${manualActId}`,{
      method: 'PUT',
      headers: {
      'Authorization': authHeader,
      'Content-Type': 'application/json',
      }, 
      body: JSON.stringify({name: name, description: description, distance: distance, duration: duration, pace: pace, fcAvg: fcAvg, materialsId: materialsId, date: date})
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
      throw error;
    });
}

export const getRunningResult = async (resultId) => {
  const authHeader = `Bearer ${store.getState().auth.token}`
  const type = "RunningResult"
  return fetch(`${END_POINT_RESULTS}/${resultId}?type=${type}`,{
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
      throw error;
    });
}

export const getStrengthResult = async (resultId) => {
  const authHeader = `Bearer ${store.getState().auth.token}`
  const type = "StrengthResult"
  return fetch(`${END_POINT_RESULTS}/${resultId}?type=${type}`,{
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
      throw error; 
    });
}

export const getMobilityResult = async (resultId) => {
  const authHeader = `Bearer ${store.getState().auth.token}`
  const type = "MobilityResult"
  return fetch(`${END_POINT_RESULTS}/${resultId}?type=${type}`,{
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
      throw error; 
    });
}

export const deleteResult = async (sesionId) => {
  const authHeader = `Bearer ${store.getState().auth.token}`
  return fetch(`${END_POINT_RESULTS}/${sesionId}`,{
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
      throw error;
    });
}

export const editResult = async (resultId,type, description, effort, date, materialsId, distance, duration, pace, fcAvg) => {
  const authHeader = `Bearer ${store.getState().auth.token}`
  return fetch(`${END_POINT_RESULTS}/${resultId}`,{
      method: 'PUT',
      headers: {
      'Authorization': authHeader,
      'Content-Type': 'application/json',
      }, 
      body: JSON.stringify({type: type, description: description, effort: effort, date: date, materialsId: materialsId, distance: distance, duration: duration, pace: pace, fcAvg: fcAvg})
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
      throw error;
    });
}

