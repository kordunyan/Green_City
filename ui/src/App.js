import GarbageCollectorForm from './components/GarbageCollectorForm';
import {
  BrowserRouter as Router,
  Switch,
  Route,
} from 'react-router-dom';
import CompanyHistory from './components/CompanyHistory';

import './App.css';

const App = () => {
    return (
        <div className='App'>
            <Router>
              <Switch>
                <Route path='/expeditor'>
                  <GarbageCollectorForm />
                </Route>
                <Route path='/resident'>
                  <CompanyHistory />
                </Route>
              </Switch>
            </Router>
        </div>
    );
}

export default App;
