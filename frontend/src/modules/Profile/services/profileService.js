const END_POINT_RUNNER = "http://localhost:8081/api/v1/runners"
const END_POINT_TRAINER = "http://localhost:8081/api/v1/trainers"
const END_POINT_USERS = "http://localhost:8081/api/v1/users"
export const updateRunnerProfile = async (id,name, surname, username, mail, height, weight, fcMax) => {
    const authHeader = `Bearer ${localStorage.getItem('token')}`
    return fetch(`${END_POINT_RUNNER}/${id}`,{
        method: 'PUT',
        headers: {
        'Authorization': authHeader,
        'Content-Type': 'application/json',
        },
        body: JSON.stringify({name: name, surname: surname, username: username, mail: mail, height: height, weight: weight, fcMax: fcMax})
    })
    .then(response => {
        return response.json();
      })
      .then(data => {
        return data
      })
      .catch(error => {
        console.error('Error:', error);
        throw error;
      });
}

export const updateTrainerProfile = async (id,name, surname, username, mail, height, weight, fcMax, experience) => {
    const authHeader = `Bearer ${localStorage.getItem('token')}`
    return fetch(`${END_POINT_TRAINER}/${id}`,{
        method: 'PUT',
        headers: {
        'Authorization': authHeader,
        'Content-Type': 'application/json',
        },
        body: JSON.stringify({name: name, surname: surname, username: username, mail: mail, height: height, weight: weight, fcMax: fcMax, experience: experience})
    })
    .then(response => {
        return response.json();
      })
      .then(data => {
        return data
      })
      .catch(error => {
        console.error('Error:', error);
        throw error;
      });
}

export const getRunnerZones = async (id) => {
  const authHeader = `Bearer ${localStorage.getItem('token')}`
    return fetch(`${END_POINT_RUNNER}/${id}/zones`,{
        method: 'GET',
        headers: {
        'Authorization': authHeader,
        'Content-Type': 'application/json',
        }
    })
    .then(response => {
        return response.json();
      })
      .then(data => {
        return data
      })
      .catch(error => {
        console.error('Error:', error);
        throw error; 
      });
}

export const getTrainerZones = async (id) => {
  const authHeader = `Bearer ${localStorage.getItem('token')}`
    return fetch(`${END_POINT_TRAINER}/${id}/zones`,{
        method: 'GET',
        headers: {
        'Authorization': authHeader,
        'Content-Type': 'application/json',
        }
    })
    .then(response => {
        return response.json();
      })
      .then(data => {
        return data
      })
      .catch(error => {
        console.error('Error:', error);
        throw error; 
      });
}

export const uploadPicture = async (id, formData) => {
  const authHeader = `Bearer ${localStorage.getItem('token')}`
    return fetch(`${END_POINT_USERS}/${id}`,{
        method: 'PUT',
        headers: {
        'Authorization': authHeader,
        },
        body: formData
    })
    .then(response => {
        return response.json();
      })
      .then(data => {
        return data
      })
      .catch(error => {
        console.error('Error:', error);
        throw error;
      });
}



