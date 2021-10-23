import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TrashCompany from './trash-company';
import TrashCompanyDetail from './trash-company-detail';
import TrashCompanyUpdate from './trash-company-update';
import TrashCompanyDeleteDialog from './trash-company-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TrashCompanyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TrashCompanyUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TrashCompanyDetail} />
      <ErrorBoundaryRoute path={match.url} component={TrashCompany} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TrashCompanyDeleteDialog} />
  </>
);

export default Routes;
