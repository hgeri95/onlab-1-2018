import React from 'react';

export function renderError(errorMessage) {
    if (errorMessage) {
        return (
            <div className="alert alert-danger">
                <string>Oops! {errorMessage.toString()}</string>
            </div>
        );
    }
}