require 'rspec'
require 'http'
require 'json'
require 'minisculus'

describe 'the minisculus challenges' do

  let(:open_instructions_in_browser?) { true }

  before(:all) do
    start
  end

  it 'should all pass' do
    challenge_one
    #challenge_two
    #etc

  end

  def challenge_one
    challenge = start_challenge
    challenge.should == "Strong NE Winds!"

    answer = "foo"

    submit_answer(answer)
  end

  private

  def client
    HTTP['Accept' => 'application/json', 'Content-Type' => 'application/json']
  end

  def start
    minisculus_host = 'http://minisculuschallenge.com'
    start_response = client.get("#{minisculus_host}/start")
    start_response.code.should == 303
    @question_url = "#{minisculus_host}#{start_response[:Location]}"
  end

  def start_challenge
    challenge_response = client.get(@question_url)
    challenge_response.code.should == 200
    response = JSON.parse(challenge_response.body)
    system('open', "http://minisculuschallenge.com/#{response['reference-url']}") if open_instructions_in_browser?
    response['question']
  end

  def submit_answer(answer)
    response = put_answer(answer)
    response.code.should == 303
    @question_url = "http://minisculuschallenge.com#{response[:Location]}"
  end

  def put_answer(answer)
    client.put(@question_url, :body => {'answer' => answer}.to_json)
  end
end