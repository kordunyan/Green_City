import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TrashExportation from './trash-exportation';
import TrashExportationDetail from './trash-exportation-detail';
import TrashExportationUpdate from './trash-exportation-update';
import TrashExportationDeleteDialog from './trash-exportation-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TrashExportationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TrashExportationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TrashExportationDetail} />
      <ErrorBoundaryRoute path={match.url} component={TrashExportation} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TrashExportationDeleteDialog} />
  </>
);

export default Routes;
