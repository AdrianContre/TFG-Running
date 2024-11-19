const USER_ENDPOINT = "http://localhost:8081/api/v1/users"

const GROUP_ENDPOINT = "http://localhost:8081/api/v1/groups"

export const getAllUsers = async () => {
    const authHeader = `Bearer ${localStorage.getItem('token')}`
    return fetch(USER_ENDPOINT,{
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
        console.log(data);
        return data;
      })
      .catch(error => {
        console.error('Error:', error);
        throw error;
      });
}

export const createGroup = async (name, description, trainerId, runnersId) => {
    const authHeader = `Bearer ${localStorage.getItem('token')}`
    return fetch(GROUP_ENDPOINT,{
        method: 'POST',
        headers: {
        'Authorization': authHeader,
        'Content-Type': 'application/json',
        },
        body: JSON.stringify({name: name, description: description, trainerId: trainerId, runnersId: runnersId})
    })
    .then(response => {
        if (!response.ok) {
            return response.json().then((errorData) => {
                throw { status: response.status, ...errorData };
            });
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