import { render, screen } from '@testing-library/react';
import App from './App';

test('renders the main storefront shell', () => {
  render(<App />);
  expect(screen.getByText(/sportshop/i)).toBeInTheDocument();
  expect(screen.getByText(/explorar productos/i)).toBeInTheDocument();
});
