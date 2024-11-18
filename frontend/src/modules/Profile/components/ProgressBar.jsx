import React from 'react';
import '../styles/progressBar.css'

function ProgressBar({ current, limit }) {

    const percentage = Math.min((current / limit) * 100, 100);
    return (
        
        <div className="progress-bar-container">
            <div className="progress-bar-filler" style={{ width: `${percentage}%` }}>
                <span className="progress-bar-label">{`${Math.round(percentage)}%`}</span>
            </div>
        </div>
    );
}

export default ProgressBar;
