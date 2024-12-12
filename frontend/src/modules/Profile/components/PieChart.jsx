import React from 'react';
import { Pie } from 'react-chartjs-2';
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';
import '../styles/pieChart.css'

ChartJS.register(ArcElement, Tooltip, Legend);

const PieChart = ({ distribution }) => {
  const data = {
    labels: ['Actividades de Running', 'Actividades de Fuerza', 'Actividades de Movilidad'],
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
    <div className='pie-container'>
        <Pie data={data} options={options} />
      </div>
      
  );
};

export default PieChart;
