import { ChatbotWebEmulatorPage } from './app.po';

describe('chatbot-web-emulator App', () => {
  let page: ChatbotWebEmulatorPage;

  beforeEach(() => {
    page = new ChatbotWebEmulatorPage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!');
  });
});
