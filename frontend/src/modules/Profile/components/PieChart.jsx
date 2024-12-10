import React from 'react';
import { Pie } from 'react-chartjs-2';
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';

ChartJS.register(ArcElement, Tooltip, Legend);

const PieChart = ({ distribution }) => {
  const data = {
    labels: ['Actividades de Running', 'Actividades de Fuerza', 'Otras Actividades'],
    datasets: [
      {
        data: distribution,
        backgroundColor: ['#36A2EB', '#FFCE56', '#FF5733'],
        hoverBackgroundColor: ['#36A2EB', '#FFCE56', '#FF5733'],
      },
    ],
  };

  const options = {
    responsive: true,
    plugins: {
      title: {
        display: true,
        text: 'Distribución de actividades:',
        color: 'black',
        font: {
            size: 16,
            weight: 'bold',
            family: 'Arial',
        },
      },
    },
  };

  return (
    <div style={{ 
        height: '300px', 
        width: '100%', 
        display: 'flex', 
        alignItems: 'center', 
        justifyContent: 'center', 
        marginBottom: '20px' 
      }}>
        <Pie data={data} options={options} />
      </div>
      
  );
};

export default PieChart;
