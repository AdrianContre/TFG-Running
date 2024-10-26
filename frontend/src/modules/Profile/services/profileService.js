const END_POINT_RUNNER = "http://localhost:8081/api/v1/runners"
const END_POINT_TRAINER = "http://localhost:8081/api/v1/trainers"
export const updateRunnerProfile = async (id,name, surname, username, mail, height, weight, fcMax) => {
    const authHeader = `Bearer ${localStorage.getItem('token')}`
    console.log(authHeader)
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
        console.log(data);
        return data
      })
      .catch(error => {
        console.error('Error:', error);
        throw error; // Lanzar el error para que pueda ser manejado por quien llame a esta función
      });
}

export const updateTrainerProfile = async (id,name, surname, username, mail, height, weight, fcMax, experience) => {
    const authHeader = `Bearer ${localStorage.getItem('token')}`
    console.log(authHeader)
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
        console.log(data);
        return data
      })
      .catch(error => {
        console.error('Error:', error);
        throw error; // Lanzar el error para que pueda ser manejado por quien llame a esta función
      });
}

export const getRunnerZones = async (id) => {
  const authHeader = `Bearer ${localStorage.getItem('token')}`
    console.log(authHeader)
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
        //console.log(data);
        return data
      })
      .catch(error => {
        console.error('Error:', error);
        throw error; // Lanzar el error para que pueda ser manejado por quien llame a esta función
      });
}

export const getTrainerZones = async (id) => {
  const authHeader = `Bearer ${localStorage.getItem('token')}`
    console.log(authHeader)
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
        //console.log(data);
        return data
      })
      .catch(error => {
        console.error('Error:', error);
        throw error; // Lanzar el error para que pueda ser manejado por quien llame a esta función
      });
}

