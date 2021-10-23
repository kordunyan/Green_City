import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Osbb from './osbb';
import OsbbDetail from './osbb-detail';
import OsbbUpdate from './osbb-update';
import OsbbDeleteDialog from './osbb-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={OsbbUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={OsbbUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={OsbbDetail} />
      <ErrorBoundaryRoute path={match.url} component={Osbb} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={OsbbDeleteDialog} />
  </>
);

export default Routes;
