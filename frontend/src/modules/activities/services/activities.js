const END_POINT_ACTIVITIES = "http://localhost:8081/api/v1/activities"

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